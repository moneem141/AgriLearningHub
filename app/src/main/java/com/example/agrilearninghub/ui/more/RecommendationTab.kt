package com.example.agrilearninghub.ui.more

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
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

        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text("ফসল বাছাই")
            Text("আমরা আপনার মাটির ধরন, আশেপাশের বায়ুর তাপমাত্রা ও মৌসুম নির্ধারণ করে আপনার জন্য উপযুক্ত কিছু ফসলের পরামর্শ দিয়ে থাকি।")
            OutlinedTextField(value = state.temp, onValueChange = {
                screenModel.updateTemp(it)
            }, placeholder = { Text("তাপমাত্রা") }, label = { Text("তাপমাত্রা") })
            OutlinedTextField(value = state.ph, onValueChange = {
                screenModel.updatePh(it)
            }, placeholder = { Text("মাটির পিএইচ") }, label = { Text("মাটির পিএইচ") })

            ExposedDropdownMenuBox(
                expanded = state.isSeasonDropdownExpanded,
                onExpandedChange = { screenModel.toggleSeasonDropdown() }
            ) {
                OutlinedTextField(
                    value = state.seasonEn ?: "",
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
                    value = state.soilType ?: "",
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
                    value = state.waterNeeds ?: "",
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
        }
    }
}