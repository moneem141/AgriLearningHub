package com.example.agrilearninghub.ui.more

import android.util.Log
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.agrilearninghub.data.Crops
import com.example.agrilearninghub.data.Season
import com.example.agrilearninghub.data.Soil
import com.example.agrilearninghub.data.WaterNeeds
import com.example.agrilearninghub.domain.repository.CropsRepository
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecommendationScreenModel(
    private val repository: CropsRepository
) : StateScreenModel<RecommendationScreenModel.State>(State()) {
    data class State(
        val crops: List<Crops> = emptyList(),
        val filteredCrops: List<Crops> = emptyList(),
        val temp: String = "",
        val ph: String = "",
        val isLoading: Boolean = true,
        val seasonEn: String? = null,
        val seasonBn: String? = null,
        val soilTypeEn: String? = null,
        val soilTypeBn: String? = null,
        val waterNeedsEn: String? = null,
        val waterNeedsBn: String? = null,
        val isSeasonDropdownExpanded: Boolean = false,
        val isSoilDropdownExpanded: Boolean = false,
        val isWaterNeedsDropdownExpanded: Boolean = false,
        val showRecommendation: Boolean = false,
        val showErrorDialog: Boolean = false,
        val checkedPassed: Boolean = false
    )

    init {
        screenModelScope.launch {
            repository.getAllCropsAsFlow().collect { crops ->
                mutableState.update { state ->
                    state.copy(
                        crops = crops,
                        filteredCrops =
                            if (mutableState.value.checkedPassed) {
                                filterCrops(crops)
                            } else {
                                emptyList()
                            }
                    )
                }
                if (crops.isEmpty()) {
                    syncLocalDatabaseFromCloud()
                } else {
                    mutableState.update { state ->
                        state.copy(
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    fun updateCheckedPassed(checkedPassed: Boolean) {
        mutableState.update { state ->
            state.copy(checkedPassed = checkedPassed)
        }
        if (checkedPassed) {
            mutableState.update { state ->
                state.copy(filteredCrops = filterCrops(state.crops))
            }
        }
    }

    fun updateTemp(temp: String) {
        mutableState.update { state ->
            state.copy(temp = temp)
        }
    }

    fun updatePh(ph: String) {
        mutableState.update { state ->
            state.copy(ph = ph)
        }
    }

    fun toggleSeasonDropdown() {
        mutableState.update { state ->
            state.copy(isSeasonDropdownExpanded = !state.isSeasonDropdownExpanded)
        }
    }

    fun toggleSoilDropdown() {
        mutableState.update { state ->
            state.copy(isSoilDropdownExpanded = !state.isSoilDropdownExpanded)
        }
    }

    fun toggleWaterNeedsDropdown() {
        mutableState.update { state ->
            state.copy(isWaterNeedsDropdownExpanded = !state.isWaterNeedsDropdownExpanded)
        }
    }

    fun updateSeason(seasonBn: String) {
        val seasonEn = Season.entries.find { it.seasonBn == seasonBn }?.name
        mutableState.update { state ->
            state.copy(seasonBn = seasonBn, seasonEn = seasonEn, isSeasonDropdownExpanded = false)
        }
    }

    fun updateSoilType(soilBn: String) {
        val soilTypeEn = Soil.entries.find { it.soilBn == soilBn }?.name
        mutableState.update { state ->
            state.copy(soilTypeBn = soilBn, soilTypeEn = soilTypeEn, isSoilDropdownExpanded = false)
        }
        Log.d("RecommendationScreenModel", "soilType: $soilBn")
    }

    fun updateWaterNeeds(waterNeedsBn: String) {
        val waterNeedsEn = WaterNeeds.entries.find { it.waterNeedsBn == waterNeedsBn }?.name
        mutableState.update { state ->
            state.copy(waterNeedsBn = waterNeedsBn, waterNeedsEn = waterNeedsEn, isWaterNeedsDropdownExpanded = false)
        }
    }

    private fun syncLocalDatabaseFromCloud() {
        screenModelScope.launch {
            repository.syncLocalDatabaseFromCloud()
            val crops = repository.getAllCrops()
            mutableState.update { state ->
                state.copy(
                    isLoading = false,
                    crops = crops
                )
            }
        }
    }

    private fun filterCrops(crops: List<Crops>): List<Crops> {
        return crops.filter { crop ->
            val temp = crop.tempLow <= mutableState.value.temp.toLong() && mutableState.value.temp.toLong() <= crop.tempHigh
            val ph = crop.phLow <= mutableState.value.ph.toDouble() && mutableState.value.ph.toDouble() <= crop.phHigh
            val season = mutableState.value.seasonEn?.let { crop.seasonEn.contains(it, ignoreCase = true) } ?: true
            val soil = mutableState.value.soilTypeEn?.let { crop.soilType.contains(it, ignoreCase = true) } ?: true
            val waterNeeds = mutableState.value.waterNeedsEn?.let { crop.waterNeeds.contains(it, ignoreCase = true) } ?: true
            temp && ph && soil && season && waterNeeds
        }
    }

    fun showRecommendation() {
        mutableState.update { state ->
            state.copy(
                showRecommendation = true
            )
        }
    }

    fun hideRecommendation() {
        mutableState.update { state ->
            state.copy(
                showRecommendation = false
            )
        }
    }

    fun showErrorDialog() {
        mutableState.update { state ->
            state.copy(showErrorDialog = true)
        }
    }

    fun hideErrorDialog() {
        mutableState.update { state ->
            state.copy(showErrorDialog = false)
        }
    }
}