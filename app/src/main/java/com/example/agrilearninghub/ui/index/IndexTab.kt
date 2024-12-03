package com.example.agrilearninghub.ui.index

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ListAlt
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.example.agrilearninghub.ui.core.screen.EmptyScreen

object IndexTab : Tab {
    private fun readResolve(): Any = IndexTab

    override val options: TabOptions
        @Composable get() {
            return TabOptions(
                index = 1u,
                title = "ফসল তালিকা",
                icon =
                    rememberVectorPainter(
                        image = Icons.AutoMirrored.Filled.ListAlt
                    )
            )
        }

    @Composable
    override fun Content() {
        EmptyScreen("Coming Screen")
    }
}