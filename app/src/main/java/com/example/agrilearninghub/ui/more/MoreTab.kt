package com.example.agrilearninghub.ui.more

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.example.agrilearninghub.R
import com.example.agrilearninghub.ui.core.screen.EmptyScreen

object MoreTab : Tab {
    private fun readResolve(): Any = MoreTab

    override val options: TabOptions
        @Composable get() {
            return TabOptions(
                index = 2u,
                title = "ফসল বাছাই",
                icon = painterResource(id = R.drawable.bulb)
            )
        }

    @Composable
    override fun Content() {
        EmptyScreen("Coming Soon")
    }
}