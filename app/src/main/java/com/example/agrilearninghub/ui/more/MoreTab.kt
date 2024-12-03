package com.example.agrilearninghub.ui.more

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.example.agrilearninghub.ui.core.screen.EmptyScreen

object MoreTab : Tab {
    private fun readResolve(): Any = MoreTab

    override val options: TabOptions
        @Composable get() {
            return TabOptions(
                index = 2u,
                title = "আরও",
                icon =
                    rememberVectorPainter(
                        image = Icons.Default.MoreHoriz
                    )
            )
        }

    @Composable
    override fun Content() {
        EmptyScreen("Coming Soon")
    }
}