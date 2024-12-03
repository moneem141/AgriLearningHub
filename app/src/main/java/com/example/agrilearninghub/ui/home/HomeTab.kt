package com.example.agrilearninghub.ui.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.example.agrilearninghub.ui.core.screen.EmptyScreen

object HomeTab : Tab {
    private fun readResolve(): Any = HomeTab

    override val options: TabOptions
        @Composable get() {
            return TabOptions(
                index = 0u,
                title = "হোম",
                icon =
                    rememberVectorPainter(
                        image = Icons.Default.Home
                    )
            )
        }

    @Composable
    override fun Content() {
        EmptyScreen("Coming Soon")
    }
}