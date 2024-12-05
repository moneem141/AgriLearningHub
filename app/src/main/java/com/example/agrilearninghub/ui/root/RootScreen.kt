package com.example.agrilearninghub.ui.root

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.example.agrilearninghub.ui.home.HomeTab
import com.example.agrilearninghub.ui.index.IndexTab
import com.example.agrilearninghub.ui.more.RecommendationTab

object RootScreen : Screen {
    private fun readResolve(): Any = RootScreen

    private val tabs =
        listOf(
            HomeTab,
            IndexTab,
            RecommendationTab
        )

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        TabNavigator(
            tab = HomeTab
        ) { tabNavigator ->
            CompositionLocalProvider(LocalNavigator provides navigator) {
                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            tabs.forEachIndexed { index, tab ->
                                NavigationBarItem(tab = tab)
                            }
                        }
                    },
                    contentWindowInsets = WindowInsets(0),
                ) { innerPadding ->
                    Box(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                                .consumeWindowInsets(innerPadding)
                    ) {
                        AnimatedContent(
                            targetState = tabNavigator.current,
                            transitionSpec = {
                                fadeIn(initialAlpha = 0.5f) togetherWith fadeOut(targetAlpha = 0.5f)
                            },
                            label = "Content"
                        ) {
                            tabNavigator.saveableState(key = "currentTab", it) {
                                it.Content()
                            }
                        }
                    }
                }
            }
            BackHandler(
                enabled = tabNavigator.current != HomeTab,
                onBack = {
                    tabNavigator.current = HomeTab
                }
            )
        }
    }

    @Composable
    private fun RowScope.NavigationBarItem(tab: Tab) {
        val tabNavigator = LocalTabNavigator.current
        val selected = tabNavigator.current::class == tab::class
        NavigationBarItem(
            selected = selected,
            onClick = {
                if (!selected) {
                    tabNavigator.current = tab
                }
            },
            icon = {
                Icon(
                    painter = tab.options.icon!!,
                    contentDescription = tab.options.title,
                    tint = LocalContentColor.current,
                )
            },
            label = {
                Text(
                    text = tab.options.title,
                    style = MaterialTheme.typography.labelLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            },
            alwaysShowLabel = true,
        )
    }
}
