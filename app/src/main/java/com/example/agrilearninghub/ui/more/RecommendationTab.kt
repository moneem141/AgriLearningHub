package com.example.agrilearninghub.ui.more

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import coil3.compose.rememberAsyncImagePainter
import com.example.agrilearninghub.R
import com.example.agrilearninghub.data.Season
import com.example.agrilearninghub.data.Soil
import com.example.agrilearninghub.data.WaterNeeds

object RecommendationTab : Tab {
    private fun readResolve(): Any = RecommendationTab

    override val options: TabOptions
        @Composable get() {
            return TabOptions(
                index = 2u,
                title = "ফসল বাছাই",
                icon = painterResource(id = R.drawable.bulb)
            )
        }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<RecommendationScreenModel>()
        val state by screenModel.state.collectAsStateWithLifecycle()

        Column {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painterResource(id = R.drawable.asset_4),
                    contentDescription = "Recommendation_Image",
                    modifier =
                        Modifier
                            .size(150.dp)
                            .align(Alignment.TopStart)
                )
                Text("ফসল বাছাই", fontSize = 24.sp, modifier = Modifier.padding(top = 48.dp))
            }
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(
                    "আমরা আপনার মাটির ধরন, আশেপাশের বায়ুর তাপমাত্রা ও মৌসুম নির্ধারণ করে আপনার জন্য উপযুক্ত কিছু ফসলের পরামর্শ দিয়ে থাকি।",
                    textAlign = TextAlign.Center
                )
                OutlinedTextField(
                    value = state.temp,
                    onValueChange = {
                        screenModel.updateTemp(it)
                    },
                    placeholder = { Text("তাপমাত্রা") },
                    label = { Text("তাপমাত্রা") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
                )
                OutlinedTextField(
                    value = state.ph,
                    onValueChange = {
                        screenModel.updatePh(it)
                    },
                    placeholder = { Text("মাটির পিএইচ") },
                    label = { Text("মাটির পিএইচ") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Next)
                )

                ExposedDropdownMenuBox(
                    expanded = state.isSeasonDropdownExpanded,
                    onExpandedChange = { screenModel.toggleSeasonDropdown() }
                ) {
                    OutlinedTextField(
                        value = state.seasonBn ?: "",
                        onValueChange = { },
                        placeholder = { Text("মৌসুম নির্বাচন করুন") },
                        readOnly = true,
                        label = { Text("মৌসুম নির্বাচন করুন") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = state.isSeasonDropdownExpanded) },
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = state.isSeasonDropdownExpanded,
                        onDismissRequest = { screenModel.toggleSeasonDropdown() }
                    ) {
                        Season.entries.forEach { season ->
                            DropdownMenuItem(
                                text = { Text(season.seasonBn) },
                                onClick = { screenModel.updateSeason(season.seasonBn) }
                            )
                        }
                    }
                }

                ExposedDropdownMenuBox(
                    expanded = state.isSoilDropdownExpanded,
                    onExpandedChange = { screenModel.toggleSoilDropdown() }
                ) {
                    OutlinedTextField(
                        value = state.soilTypeBn ?: "",
                        onValueChange = {},
                        placeholder = { Text("মাটির ধরন নির্বাচন করুন") },
                        readOnly = true,
                        label = { Text("মাটির ধরন নির্বাচন করুন") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = state.isSoilDropdownExpanded) },
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = state.isSoilDropdownExpanded,
                        onDismissRequest = { screenModel.toggleSoilDropdown() }
                    ) {
                        Soil.entries.forEach { soil ->
                            DropdownMenuItem(
                                text = { Text(soil.soilBn) },
                                onClick = { screenModel.updateSoilType(soil.soilBn) }
                            )
                        }
                    }
                }

                ExposedDropdownMenuBox(
                    expanded = state.isWaterNeedsDropdownExpanded,
                    onExpandedChange = { screenModel.toggleWaterNeedsDropdown() }
                ) {
                    OutlinedTextField(
                        value = state.waterNeedsBn ?: "",
                        onValueChange = {},
                        placeholder = { Text("পানির চাহিদা নির্বাচন করুন") },
                        readOnly = true,
                        label = { Text("পানির চাহিদা নির্বাচন করুন") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = state.isWaterNeedsDropdownExpanded) },
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = state.isWaterNeedsDropdownExpanded,
                        onDismissRequest = { screenModel.toggleWaterNeedsDropdown() }
                    ) {
                        WaterNeeds.entries.forEach { waterNeeds ->
                            DropdownMenuItem(
                                text = { Text(waterNeeds.waterNeedsBn) },
                                onClick = { screenModel.updateWaterNeeds(waterNeeds.waterNeedsBn) }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.size(16.dp))
                Button(
                    onClick = {
                        val isValid = state.temp.isNotEmpty() && state.ph.isNotEmpty()
                        screenModel.updateCheckedPassed(isValid)
                        if (!isValid) {
                            screenModel.showErrorDialog()
                        } else {
                            screenModel.showRecommendation()
                        }
                    },
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF63A002),
                            contentColor = MaterialTheme.colorScheme.onBackground
                        )
                ) {
                    Text(
                        text = "পরামর্শ",
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
            }
        }

        if (state.showErrorDialog) {
            AlertDialog(
                icon = null,
                text = {
                    Text("তথ্য পূরণ করুন")
                },
                onDismissRequest = {
                    screenModel.hideErrorDialog()
                },
                confirmButton = {
                    Button(
                        onClick = { screenModel.hideErrorDialog() }
                    ) {
                        Text("ঠিক আছে")
                    }
                },
                dismissButton = null
            )
        }
        if (state.showRecommendation) {
            BasicAlertDialog(
                onDismissRequest = { screenModel.hideRecommendation() }
            ) {
                if (state.filteredCrops.isEmpty()) {
                    AlertDialog(
                        icon = null,
                        text = {
                            Text("কোন ফসল পাওয়া যায়নি")
                        },
                        onDismissRequest = {
                            screenModel.hideRecommendation()
                        },
                        confirmButton = {
                            Button(
                                onClick = { screenModel.hideRecommendation() }
                            ) {
                                Text("ঠিক আছে")
                            }
                        },
                        dismissButton = null
                    )
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Card(
                            modifier = Modifier.padding(8.dp),
                            shape = RoundedCornerShape(8.dp),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Text(
                                text = "আপনার জন্য উপযুক্ত ফসল:",
                                textAlign = TextAlign.Center,
                                modifier =
                                    Modifier
                                        .padding(16.dp)
                                        .statusBarsPadding()
                            )
                        }
                        LazyColumn {
                            items(
                                items = state.filteredCrops.toList(),
                                key = { crop -> crop.id }
                            ) { crop ->
                                Card(
                                    modifier = Modifier.padding(8.dp),
                                    shape = RoundedCornerShape(8.dp),
                                    elevation = CardDefaults.cardElevation(4.dp)
                                ) {
                                    ListItem(
                                        headlineContent = { Text(crop.nameBn) },
                                        leadingContent = {
                                            Image(
                                                painter =
                                                    rememberAsyncImagePainter(
                                                        crop.image,
                                                        filterQuality = FilterQuality.High
                                                    ),
                                                contentDescription = "Crop image",
                                                modifier = Modifier.size(56.dp),
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
}