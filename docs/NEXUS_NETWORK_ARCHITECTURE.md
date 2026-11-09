# NEXUS NETWORK — Architecture (Phase 0)

## Goal

Expose a Minecraft Java server running on the Android host device to invited friends over the public internet, without router port forwarding. The game server must continue to use the phone's own CPU and RAM. The relay only forwards network traffic; it does not host the Minecraft world.

## Important infrastructure constraint

A phone behind carrier-grade NAT cannot normally accept unsolicited inbound internet connections. A public relay therefore needs a publicly reachable machine with an address and sufficient bandwidth. The relay software and Android client can be open source and free to build, but a permanently available public relay is not guaranteed to be free. Do not use GitHub Actions as a permanent relay host.

## Components

1. **NEXUS Android Host Agent (Kotlin):** Runs as a foreground service while hosting is active. It connects outward to the relay over authenticated TLS, registers a tunnel, reconnects with bounded backoff, and forwards accepted player streams to the local Minecraft server at 127.0.0.1:25565.
2. **NEXUS Relay (Rust, separate service):** Runs on a public Linux host. It authenticates host agents, allocates a public TCP endpoint per tunnel, accepts Minecraft player connections, and forwards byte streams to the matching connected host agent.
3. **NEXUS Control API (small service):** Issues short-lived host credentials, creates/revokes tunnel registrations, and returns the public address. It must not expose relay secrets to the Android UI or commit secrets to Git.
4. **NEXUS Hosting UI (Jetpack Compose):** Create/start/stop server, connect/disconnect tunnel, display address, online state, bytes transferred, and safe error messages.

## Minecraft Java traffic

Minecraft Java Edition's normal multiplayer connection is TCP. The first milestone supports TCP only (default local port 25565). Bedrock/UDP and voice-chat UDP are explicitly out of scope for the first milestone.

## Connection flow

1. Android starts the local Minecraft server on the phone.
2. Host Agent opens an outbound TLS control connection to the relay and authenticates with a short-lived token.
3. Relay allocates a public TCP port and reports the public host/port to the app.
4. A friend connects to that public endpoint using Minecraft Java.
5. Relay notifies the Host Agent about the incoming connection.
6. Host Agent opens an outbound authenticated data connection; relay pairs the two TCP streams and forwards bytes in both directions.
7. On stop, the app closes the tunnel and the relay releases the public port.

## Security requirements

- TLS for control and data channels.
- Short-lived, revocable per-device credentials; never embed a shared master secret in the APK.
- Random tunnel identifiers and authorization checks on every registration.
- Rate limits, connection caps, bandwidth accounting, and idle timeouts.
- Relay accepts only the Minecraft TCP tunnel service in the first release; no arbitrary shell, file access, or remote-control features.
- Do not log access tokens or full private connection credentials.
- Explicit host consent before making a server public; provide a one-tap stop/revoke action.

## Implementation milestones

### Phase 1 — Protocol proof of concept
- Define a versioned control protocol and message framing.
- Implement a local mock relay and a desktop test client.
- Test bidirectional TCP forwarding to a local echo server before connecting Minecraft.

### Phase 2 — Android host agent
- Add a Kotlin tunnel client module.
- Implement TLS control connection, token storage, foreground-service lifecycle, reconnect/backoff, and local TCP forwarding.
- Keep the agent independent from Minecraft process lifecycle until the basic tunnel test passes.

### Phase 3 — Public relay
- Implement Rust relay listener, tunnel registry, port allocator, connection pairing, limits, and health endpoint.
- Deploy only to a machine with a public IP and configured firewall.
- Add metrics and operational shutdown/restart handling.

### Phase 4 — Launcher integration
- Add Hosting screen state: Server stopped/running, Tunnel disconnected/connected, public address, and Stop.
- Bind tunnel lifecycle to the existing server lifecycle without blocking the UI thread.
- Test on Android mobile data and a second network.

### Phase 5 — Reliability
- Test disconnect/reconnect, app backgrounding, phone sleep, server crash, relay restart, multiple players, and bandwidth limits.
- Document limitations and show the user when the relay is unavailable.

## First acceptance test

A local echo service on the Android device must be reachable through a relay from a second network. Only after that succeeds should the tunnel target be changed to the Minecraft server port.
