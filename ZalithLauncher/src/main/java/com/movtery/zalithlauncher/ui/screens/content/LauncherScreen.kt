/*
 * Zalith Launcher 2
 * Copyright (C) 2025 MovTery <movtery228@qq.com> and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/gpl-3.0.txt>.
 */

package com.movtery.zalithlauncher.ui.screens.content

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.movtery.zalithlauncher.BuildConfig
import com.movtery.zalithlauncher.BuildKeys
import com.movtery.zalithlauncher.R
import com.movtery.zalithlauncher.game.account.AccountsManager
import com.movtery.zalithlauncher.game.version.installed.Version
import com.movtery.zalithlauncher.game.version.installed.VersionsManager
import com.movtery.zalithlauncher.ui.base.BaseScreen
import com.movtery.zalithlauncher.ui.components.BackgroundCard
import com.movtery.zalithlauncher.ui.components.MarqueeText
import com.movtery.zalithlauncher.ui.components.ScalingActionButton
import com.movtery.zalithlauncher.ui.components.defaultRichTextStyle
import com.movtery.zalithlauncher.ui.screens.NestedNavKey
import com.movtery.zalithlauncher.ui.screens.NormalNavKey
import com.movtery.zalithlauncher.ui.screens.content.elements.AccountAvatar
import com.movtery.zalithlauncher.ui.screens.content.elements.CommonVersionInfoLayout
import com.movtery.zalithlauncher.ui.screens.content.elements.VersionIconImage
import com.movtery.zalithlauncher.ui.screens.content.navigateToDownload
import com.movtery.zalithlauncher.ui.screens.main.custom_home.MarkdownBlock
import com.movtery.zalithlauncher.ui.screens.main.custom_home.customHomePage
import com.movtery.zalithlauncher.utils.animation.swapAnimateDpAsState
import com.movtery.zalithlauncher.viewmodel.HomePageState
import com.movtery.zalithlauncher.viewmodel.LocalHomePageViewModel
import com.movtery.zalithlauncher.viewmodel.ScreenBackStackViewModel

@Composable
fun LauncherScreen(
    backStackViewModel: ScreenBackStackViewModel,
    navigateToVersions: (Version) -> Unit,
    onLaunchGame: (Version?) -> Unit,
    onOpenLink: (String) -> Unit,
    onHomePageEvent: (MarkdownBlock.Button.Event) -> Unit,
) {
    BaseScreen(
        screenKey = NormalNavKey.LauncherMain,
        currentKey = backStackViewModel.mainScreen.currentKey
    ) { isVisible ->
        Column(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 12.dp, top = 8.dp, bottom = 4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().height(42.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(1.dp)) {
                        Text(
                            text = "NEXUS LAUNCHER",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "MINECRAFT JAVA EDITION  •  GAME HUB",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "JAVA",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    listOf(
                        "MODS" to backStackViewModel.downloadModScreen,
                        "RESOURCE PACKS" to backStackViewModel.downloadResourcePackScreen,
                        "SHADERS" to backStackViewModel.downloadShadersScreen,
                        "MODPACKS" to backStackViewModel.downloadModPackScreen
                    ).forEach { (label, destination) ->
                        Text(
                            text = label,
                            modifier = Modifier
                                .clip(MaterialTheme.shapes.large)
                                .clickable { backStackViewModel.navigateToDownload(destination) }
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1
                        )
                    }
                    Text(
                        text = "VERSION MANAGER",
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.large)
                            .clickable {
                                VersionsManager.currentVersion.value?.let(navigateToVersions)
                            }
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary,
                        maxLines = 1
                    )
                }
            }
            androidx.compose.foundation.layout.BoxWithConstraints(
                modifier = Modifier.fillMaxWidth()
            ) {
                val toAccountManageScreen: () -> Unit = {
                    backStackViewModel.mainScreen.navigateTo(
                        screenKey = NormalNavKey.AccountManager(FirstLoginMenu.NONE)
                    )
                }
                val toVersionManageScreen: () -> Unit = {
                    backStackViewModel.mainScreen.removeAndNavigateTo(
                        remove = NestedNavKey.VersionSettings::class,
                        screenKey = NormalNavKey.VersionsManager
                    )
                }
                val toVersionSettingsScreen: () -> Unit = {
                    VersionsManager.currentVersion.value?.let { version ->
                        navigateToVersions(version)
                    }
                }

                if (maxWidth < 10000.dp) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFF050B16))
                            .verticalScroll(rememberScrollState())
                    ) {
                        // Compact launcher navigation inspired by the reference's left rail.
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState())
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            listOf(
                                "⌂  HOME" to null,
                                "▣  INSTALLATIONS" to "versions",
                                "✣  MODS" to "mods",
                                "▱  RESOURCE PACKS" to "packs",
                                "☼  SHADERS" to "shaders",
                                "⬡  MODPACKS" to "modpacks"
                            ).forEach { (label, target) ->
                                val selected = target == null
                                BackgroundCard(
                                    modifier = Modifier
                                        .clip(MaterialTheme.shapes.medium)
                                        .clickable {
                                            when (target) {
                                                "versions" -> toVersionManageScreen()
                                                "mods" -> backStackViewModel.navigateToDownload(backStackViewModel.downloadModScreen)
                                                "packs" -> backStackViewModel.navigateToDownload(backStackViewModel.downloadResourcePackScreen)
                                                "shaders" -> backStackViewModel.navigateToDownload(backStackViewModel.downloadShadersScreen)
                                                "modpacks" -> backStackViewModel.navigateToDownload(backStackViewModel.downloadModPackScreen)
                                            }
                                        },
                                    shape = MaterialTheme.shapes.medium
                                ) {
                                    Text(
                                        text = label,
                                        modifier = Modifier.padding(horizontal = 13.dp, vertical = 10.dp),
                                        style = MaterialTheme.typography.labelMedium,
                                        color = if (selected) Color(0xFF78C2FF) else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                        val activeAccount by AccountsManager.currentAccountFlow.collectAsStateWithLifecycle()
                        BackgroundCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                                .clickable {
                                    backStackViewModel.mainScreen.navigateTo(
                                        screenKey = NormalNavKey.AccountManager(
                                            if (activeAccount == null) FirstLoginMenu.NORMAL else FirstLoginMenu.NONE
                                        )
                                    )
                                },
                            shape = MaterialTheme.shapes.large
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                AccountAvatar(
                                    modifier = Modifier.size(44.dp),
                                    account = activeAccount,
                                    onClick = {
                                        backStackViewModel.mainScreen.navigateTo(
                                            screenKey = NormalNavKey.AccountManager(
                                                if (activeAccount == null) FirstLoginMenu.NORMAL else FirstLoginMenu.NONE
                                            )
                                        )
                                    }
                                )
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = activeAccount?.username ?: "Offline / Microsoft Account",
                                        style = MaterialTheme.typography.titleSmall,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = if (activeAccount == null) "Choose an account type to continue" else "Selected account · Ready to play",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                                Text(
                                    text = if (activeAccount == null) "ADD ACCOUNT  +" else "SWITCH ACCOUNT  ›",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                        // Screenshot-inspired account hub: dedicated Offline and Microsoft entry cards.
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState())
                                .padding(horizontal = 12.dp, vertical = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            BackgroundCard(
                                modifier = Modifier
                                    .width(172.dp)
                                    .height(88.dp)
                                    .clickable {
                                        backStackViewModel.mainScreen.navigateTo(
                                            screenKey = NormalNavKey.AccountManager(FirstLoginMenu.NORMAL)
                                        )
                                    },
                                shape = MaterialTheme.shapes.large
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxSize().padding(12.dp),
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text("OFFLINE ACCOUNT", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
                                    Text("Play with a local profile", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Text("ADD / SELECT  ›", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurface)
                                }
                            }
                            BackgroundCard(
                                modifier = Modifier
                                    .width(172.dp)
                                    .height(88.dp)
                                    .clickable {
                                        backStackViewModel.mainScreen.navigateTo(
                                            screenKey = NormalNavKey.AccountManager(FirstLoginMenu.NORMAL)
                                        )
                                    },
                                shape = MaterialTheme.shapes.large
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxSize().padding(12.dp),
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text("MICROSOFT ACCOUNT", style = MaterialTheme.typography.labelLarge, color = Color(0xFF68B7FF))
                                    Text("Use your Microsoft login", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Text("SIGN IN / SWITCH  ›", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurface)
                                }
                            }
                        }

                        // Compact news panel in the same dark premium visual language.
                        BackgroundCard(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 6.dp),
                            shape = MaterialTheme.shapes.large
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Box(
                                    modifier = Modifier.size(44.dp).clip(MaterialTheme.shapes.medium).background(Color(0xFF172B46)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("N", style = MaterialTheme.typography.headlineSmall, color = Color(0xFF69B4FF))
                                }
                                Column(modifier = Modifier.weight(1f)) {
                                    Text("NEXUS LAUNCHER", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
                                    Text("Minecraft Java Edition", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSurface)
                                    Text("Manage versions, discover mods and customize your game.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }
                        }

                        // Premium hero banner supplied in res/drawable-nodpi/launcher_banner.png.
                        BackgroundCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                                .height(280.dp),
                            shape = MaterialTheme.shapes.extraLarge
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(MaterialTheme.shapes.extraLarge)
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.launcher_banner),
                                    contentDescription = "Minecraft Java Edition",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color(0x88050B16))
                                        .clickable { toVersionManageScreen() }
                                        .padding(16.dp)
                                ) {
                                Column(
                                    modifier = Modifier.align(Alignment.TopStart),
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = "MINECRAFT",
                                        style = MaterialTheme.typography.displaySmall,
                                        color = Color.White
                                    )
                                    Text(
                                        text = "JAVA EDITION  •  BUILD · EXPLORE · SURVIVE",
                                        style = MaterialTheme.typography.titleSmall,
                                        color = Color(0xFF76B9FF)
                                    )
                                }
                                Column(
                                    modifier = Modifier.align(Alignment.BottomStart),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    var showHeroVersionMenu by remember { mutableStateOf(false) }
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Box {
                                            BackgroundCard(
                                                modifier = Modifier
                                                    .width(156.dp)
                                                    .height(58.dp)
                                                    .clickable { showHeroVersionMenu = true },
                                                shape = MaterialTheme.shapes.medium
                                            ) {
                                                Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp)) {
                                                    Text(
                                                        text = "SELECT VERSION",
                                                        style = MaterialTheme.typography.labelSmall,
                                                        color = MaterialTheme.colorScheme.primary
                                                    )
                                                    Text(
                                                        text = VersionsManager.currentVersion.value?.getVersionName() ?: "Choose version",
                                                        style = MaterialTheme.typography.labelLarge,
                                                        color = MaterialTheme.colorScheme.onSurface,
                                                        maxLines = 1
                                                    )
                                                }
                                            }
                                            DropdownMenu(
                                                expanded = showHeroVersionMenu,
                                                onDismissRequest = { showHeroVersionMenu = false },
                                                modifier = Modifier.width(240.dp)
                                            ) {
                                                VersionsManager.versions.forEach { availableVersion ->
                                                    DropdownMenuItem(
                                                        text = {
                                                            CommonVersionInfoLayout(
                                                                version = availableVersion,
                                                                iconSize = 28.dp
                                                            )
                                                        },
                                                        onClick = {
                                                            VersionsManager.saveVersion(availableVersion)
                                                            showHeroVersionMenu = false
                                                        }
                                                    )
                                                }
                                                DropdownMenuItem(
                                                    text = { Text("Manage / Download Versions") },
                                                    onClick = {
                                                        showHeroVersionMenu = false
                                                        toVersionManageScreen()
                                                    }
                                                )
                                            }
                                        }
                                        ScalingActionButton(
                                            modifier = Modifier.width(156.dp).height(58.dp),
                                            onClick = {
                                                val selectedVersion = VersionsManager.currentVersion.value
                                                if (selectedVersion != null && selectedVersion.isValid()) {
                                                    onLaunchGame(selectedVersion)
                                                } else {
                                                    toVersionManageScreen()
                                                }
                                            }
                                        ) {
                                            Text(
                                                text = stringResource(R.string.main_launch_game),
                                                style = MaterialTheme.typography.titleMedium
                                            )
                                        }
                                    }
                                }
                                Text(
                                    text = "VERSION MANAGER  ›",
                                    modifier = Modifier
                                        .align(Alignment.BottomEnd)
                                        .clip(MaterialTheme.shapes.medium)
                                        .clickable { toVersionManageScreen() }
                                        .padding(8.dp),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.White
                                )
                                }
                            }
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState())
                                .padding(horizontal = 12.dp, vertical = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            listOf(
                                "MODS" to backStackViewModel.downloadModScreen,
                                "RESOURCE PACKS" to backStackViewModel.downloadResourcePackScreen,
                                "SHADERS" to backStackViewModel.downloadShadersScreen,
                                "MODPACKS" to backStackViewModel.downloadModPackScreen
                            ).forEach { (label, destination) ->
                                BackgroundCard(
                                    modifier = Modifier
                                        .width(174.dp)
                                        .height(94.dp)
                                        .clickable { backStackViewModel.navigateToDownload(destination) },
                                    shape = MaterialTheme.shapes.large
                                ) {
                                    Column(
                                        modifier = Modifier.fillMaxSize().padding(14.dp),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.Start
                                    ) {
                                        Text(
                                            text = when (label) { "MODS" -> "✣"; "RESOURCE PACKS" -> "▱"; "SHADERS" -> "☼"; else -> "⬡" },
                                            style = MaterialTheme.typography.headlineMedium,
                                            color = when (label) { "MODS" -> Color(0xFF65A5FF); "RESOURCE PACKS" -> Color(0xFF35D6B0); "SHADERS" -> Color(0xFFFFC15B); else -> Color(0xFFE879F9) }
                                        )
                                        Text(
                                            text = label,
                                            style = MaterialTheme.typography.titleSmall,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            maxLines = 2
                                        )
                                        Text(
                                            text = when (label) { "MODS" -> "Enhance your game"; "RESOURCE PACKS" -> "Customize visuals"; "SHADERS" -> "Upgrade graphics"; else -> "Install collections" },
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            maxLines = 1
                                        )
                                    }
                                }
                            }
                        }
                        // Installed game instances, using the launcher's real version registry.
                        Text(
                            text = "YOUR INSTALLATIONS",
                            modifier = Modifier.padding(start = 16.dp, top = 20.dp, bottom = 8.dp),
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        val installedVersions = VersionsManager.versions
                        if (installedVersions.isEmpty()) {
                            BackgroundCard(
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 4.dp),
                                shape = MaterialTheme.shapes.large
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(14.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    VersionIconImage(version = null, modifier = Modifier.size(38.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text("No installed versions", style = MaterialTheme.typography.titleSmall)
                                        Text("Create an installation to get started.", style = MaterialTheme.typography.bodySmall)
                                    }
                                    Text(
                                        "CREATE",
                                        modifier = Modifier.clickable { toVersionManageScreen() }.padding(8.dp),
                                        color = MaterialTheme.colorScheme.primary,
                                        style = MaterialTheme.typography.labelMedium
                                    )
                                }
                            }
                        } else {
                            installedVersions.take(4).forEach { installedVersion ->
                                BackgroundCard(
                                    modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 4.dp),
                                    shape = MaterialTheme.shapes.large
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth().padding(10.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                                    ) {
                                        VersionIconImage(version = installedVersion, modifier = Modifier.size(42.dp))
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                installedVersion.getVersionName(),
                                                style = MaterialTheme.typography.titleSmall,
                                                maxLines = 1
                                            )
                                            Text(
                                                if (installedVersion.isValid()) installedVersion.getVersionSummary() else "Needs setup",
                                                style = MaterialTheme.typography.bodySmall,
                                                maxLines = 1,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                        Text(
                                            "PLAY",
                                            modifier = Modifier
                                                .clip(MaterialTheme.shapes.medium)
                                                .background(MaterialTheme.colorScheme.primary)
                                                .clickable { onLaunchGame(installedVersion) }
                                                .padding(horizontal = 14.dp, vertical = 9.dp),
                                            color = MaterialTheme.colorScheme.onPrimary,
                                            style = MaterialTheme.typography.labelMedium
                                        )
                                        Text(
                                            "⋮",
                                            modifier = Modifier.clickable { navigateToVersions(installedVersion) }.padding(horizontal = 4.dp),
                                            color = MaterialTheme.colorScheme.onSurface,
                                            style = MaterialTheme.typography.titleLarge
                                        )
                                    }
                                }
                            }
                            Text(
                                "VIEW ALL  ›",
                                modifier = Modifier.align(Alignment.End).clickable { toVersionManageScreen() }.padding(horizontal = 18.dp, vertical = 8.dp),
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.labelMedium
                            )
                        }

                        Text(
                            text = "QUICK ACCESS",
                            modifier = Modifier.padding(start = 16.dp, top = 22.dp, bottom = 8.dp),
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        listOf(
                            "Browse Mods" to backStackViewModel.downloadModScreen,
                            "Browse Resource Packs" to backStackViewModel.downloadResourcePackScreen,
                            "Browse Shaders" to backStackViewModel.downloadShadersScreen,
                            "Browse Modpacks" to backStackViewModel.downloadModPackScreen
                        ).forEach { (label, destination) ->
                            BackgroundCard(
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 3.dp)
                                    .clickable { backStackViewModel.navigateToDownload(destination) },
                                shape = MaterialTheme.shapes.medium
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(label, modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodyMedium)
                                    Text("›", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.titleLarge)
                                }
                            }
                        }

                        // The mobile dashboard is self-contained above; do not render the old blank homepage panel.
                    }
                } else {
                    Row(modifier = Modifier.fillMaxSize()) {
                        CompositionLocalProvider(
                            LocalUriHandler provides object : UriHandler {
                                override fun openUri(uri: String) {
                                    onOpenLink(uri)
                                }
                            }
                        ) {
                            ContentMenu(
                                modifier = Modifier.weight(7f),
                                isVisible = isVisible,
                                onHomePageEvent = onHomePageEvent
                            )
                        }
                        RightMenu(
                            isVisible = isVisible,
                            modifier = Modifier
                                .weight(3f)
                                .fillMaxHeight()
                                .padding(top = 12.dp, end = 12.dp, bottom = 12.dp),
                            onLaunchGame = onLaunchGame,
                            toAccountManageScreen = toAccountManageScreen,
                            toVersionManageScreen = toVersionManageScreen,
                            toVersionSettingsScreen = toVersionSettingsScreen
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun ContentMenu(
    isVisible: Boolean,
    onHomePageEvent: (MarkdownBlock.Button.Event) -> Unit,
    modifier: Modifier = Modifier,
) {
    val yOffset by swapAnimateDpAsState(
        targetValue = (-40).dp,
        swapIn = isVisible
    )

    val homePageViewModel = LocalHomePageViewModel.current
    val pageState by homePageViewModel.pageState.collectAsStateWithLifecycle()
    val richTextStyle = defaultRichTextStyle()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .offset { IntOffset(x = 0, y = yOffset.roundToPx()) },
        contentPadding = PaddingValues(all = 12.dp)
    ) {
        when (val state = pageState) {
            is HomePageState.Blank -> {}
            is HomePageState.Loading -> {
                item(key = "homepage_loading_box") {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            LoadingIndicator()
                            Text(
                                text = stringResource(R.string.settings_launcher_home_page_loading),
                                style = MaterialTheme.typography.labelMedium,
                            )
                        }
                    }
                }
            }
            is HomePageState.None -> {
                customHomePage(
                    blocks = state.page,
                    richTextStyle = richTextStyle,
                    onEvent = onHomePageEvent
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RightMenuContent(
    modifier: Modifier = Modifier,
    onLaunchGame: (Version?) -> Unit,
    toAccountManageScreen: () -> Unit,
    toVersionManageScreen: () -> Unit,
    toVersionSettingsScreen: () -> Unit,
    launchButton: @Composable (
        innerModifier: Modifier,
        onClick: () -> Unit,
        text: @Composable RowScope.() -> Unit
    ) -> Unit,
) {
    val account by AccountsManager.currentAccountFlow.collectAsStateWithLifecycle()
    val version by VersionsManager.currentVersion.collectAsStateWithLifecycle()
    val isRefreshing by VersionsManager.isRefreshing.collectAsStateWithLifecycle()

    ConstraintLayout(
        modifier = modifier
    ) {
        val (accountAvatar, versionManagerLayout, launchButton) = createRefs()

        AccountAvatar(
            modifier = Modifier
                .constrainAs(accountAvatar) {
                    top.linkTo(parent.top)
                    bottom.linkTo(launchButton.top, margin = 32.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            account = account,
            onClick = toAccountManageScreen
        )

        var showList by remember { mutableStateOf(false) }
        var versionManagerRow by remember { mutableStateOf<LayoutCoordinates?>(null) }
        Box(
            modifier = Modifier.constrainAs(versionManagerLayout) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(launchButton.top)
            },
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .onGloballyPositioned { coordinates ->
                            versionManagerRow = coordinates
                        }
                ) {
                    VersionManagerLayout(
                        isRefreshing = isRefreshing,
                        version = version,
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        swapToVersionManage = toVersionManageScreen,
                        openListMenu = { showList = true },
                    )
                }
                version?.takeIf { !isRefreshing && it.isValid() }?.let {
                    IconButton(
                        modifier = Modifier.padding(end = 8.dp),
                        onClick = toVersionSettingsScreen
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_settings_filled),
                            contentDescription = stringResource(R.string.versions_manage_settings)
                        )
                    }
                }
            }

            val menuAnchor = versionManagerRow
            val menuAnchorBounds = menuAnchor?.boundsInParent()
            val menuAnchorX = menuAnchorBounds?.left ?: 0f
            val menuAnchorHeight = menuAnchorBounds?.height ?: 0f

            DropdownMenu(
                expanded = showList && menuAnchor != null,
                onDismissRequest = { showList = false },
                modifier = Modifier.width(260.dp),
                offset = DpOffset(
                    x = with(LocalDensity.current) { menuAnchorX.toDp() },
                    y = with(LocalDensity.current) { (-menuAnchorHeight).toDp() } - 8.dp
                ),
                shape = MaterialTheme.shapes.extraLarge
            ) {
                VersionsManager.versions.forEach { version0 ->
                    DropdownMenuItem(
                        text = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CommonVersionInfoLayout(
                                    modifier = Modifier.weight(1f),
                                    version = version0,
                                    iconSize = 28.dp
                                )
                                IconButton(
                                    onClick = {
                                        onLaunchGame(version0)
                                        showList = false
                                    }
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_play_arrow_filled),
                                        contentDescription = stringResource(R.string.main_launch_game),
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        },
                        onClick = {
                            if (version == version0) return@DropdownMenuItem
                            VersionsManager.saveVersion(version0)
                            showList = false
                        }
                    )
                }
            }
        }

        launchButton(
            Modifier
                .fillMaxWidth()
                .constrainAs(launchButton) {
                    bottom.linkTo(parent.bottom, margin = 8.dp)
                }
                .padding(PaddingValues(horizontal = 12.dp)),
            {
                onLaunchGame(null)
            },
            {
                MarqueeText(text = stringResource(R.string.main_launch_game))
            }
        )
    }
}

@Composable
private fun RightMenu(
    isVisible: Boolean,
    onLaunchGame: (Version?) -> Unit,
    modifier: Modifier = Modifier,
    toAccountManageScreen: () -> Unit = {},
    toVersionManageScreen: () -> Unit = {},
    toVersionSettingsScreen: () -> Unit = {}
) {
    val xOffset by swapAnimateDpAsState(
        targetValue = 40.dp,
        swapIn = isVisible,
        isHorizontal = true
    )

    BackgroundCard(
        modifier = modifier.offset { IntOffset(x = xOffset.roundToPx(), y = 0) },
        shape = MaterialTheme.shapes.extraLarge
    ) {
        RightMenuContent(
            modifier = Modifier.fillMaxSize(),
            onLaunchGame = onLaunchGame,
            toAccountManageScreen = toAccountManageScreen,
            toVersionManageScreen = toVersionManageScreen,
            toVersionSettingsScreen = toVersionSettingsScreen
        ) { innerModifier, onClick, text ->
            ScalingActionButton(
                modifier = innerModifier,
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 1.dp),
                onClick = onClick,
                content = text
            )
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun VersionManagerLayout(
    isRefreshing: Boolean,
    version: Version?,
    swapToVersionManage: () -> Unit,
    openListMenu: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clip(shape = MaterialTheme.shapes.large)
            .combinedClickable(
                role = Role.Button,
                onClick = swapToVersionManage,
                onLongClick = {
                    if (version != null) openListMenu()
                }
            )
            .padding(PaddingValues(all = 8.dp))
    ) {
        if (isRefreshing) {
            Box(modifier = Modifier.fillMaxWidth()) {
                LoadingIndicator(
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.Center)
                )
            }
        } else {
            VersionIconImage(
                version = version,
                modifier = Modifier
                    .size(28.dp)
                    .align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.width(8.dp))

            if (version == null) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .basicMarquee(iterations = Int.MAX_VALUE),
                    text = stringResource(R.string.versions_manage_no_versions),
                    style = MaterialTheme.typography.labelMedium,
                    maxLines = 1
                )
            } else {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                ) {
                    Text(
                        modifier = Modifier.basicMarquee(iterations = Int.MAX_VALUE),
                        text = version.getVersionName(),
                        style = MaterialTheme.typography.labelMedium,
                        maxLines = 1
                    )
                    if (version.isValid()) {
                        Text(
                            modifier = Modifier.basicMarquee(iterations = Int.MAX_VALUE),
                            text = version.getVersionSummary(),
                            style = MaterialTheme.typography.labelSmall,
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}