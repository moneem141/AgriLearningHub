package com.example.agrilearninghub.ui.index

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ListAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import coil3.compose.rememberAsyncImagePainter
import com.example.agrilearninghub.ui.core.screen.LoadingScreen
import com.example.agrilearninghub.ui.detail.DetailScreen

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

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val screenModel = koinScreenModel<IndexScreenModel>()
        val state by screenModel.state.collectAsStateWithLifecycle()

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("ফসল তালিকা")
                    },
                    actions = {
                        IconButton(
                            onClick = { screenModel.syncLocalDatabaseFromCloud() }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Sync,
                                contentDescription = "Refresh"
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            if (state.isLoading) {
                LoadingScreen(modifier = Modifier.padding(innerPadding))
            } else {
                Column(modifier = Modifier.padding(innerPadding)) {
                    OutlinedTextField(
                        value = state.searchQuery ?: "",
                        onValueChange = { screenModel.updateSearchQuery(it) },
                        placeholder = { Text("ফসল খুঁজুন") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search"
                            )
                        },
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp).fillMaxWidth()
                    )
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(
                            items = state.filteredCrops,
                            key = { crop -> crop.id }
                        ) { crop ->
                            Card(
                                modifier =
                                    Modifier
                                        .padding(horizontal = 16.dp, vertical = 8.dp)
                                        .clickable { navigator.push(DetailScreen(crop.id)) },
                                shape = RoundedCornerShape(8.dp),
                                elevation = CardDefaults.cardElevation(4.dp)
                            ) {
                                ListItem(
                                    headlineContent = { Text(crop.nameBn) },
                                    leadingContent = {
                                        Image(
                                            painter = rememberAsyncImagePainter(crop.image, filterQuality = FilterQuality.High),
                                            contentDescription = "Crop image",
                                            modifier =
                                                Modifier
                                                    .size(56.dp)
                                                    .clip(shape = MaterialTheme.shapes.extraSmall),
                                            contentScale = ContentScale.Crop
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}