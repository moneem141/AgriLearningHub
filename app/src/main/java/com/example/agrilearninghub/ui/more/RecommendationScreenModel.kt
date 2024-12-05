package com.example.agrilearninghub.ui.more

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.agrilearninghub.data.Crops
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
        val tempLow: Long = 0,
        val tempHigh: Long = 0,
        val phLow: Float = 0f,
        val phHigh: Float = 0f,
        val seasonEn: String? = null,
        val soilType: String? = null,
        val waterNeeds: String? = null,
        val isSeasonDropdownExpanded: Boolean = false,
        val isSoilDropdownExpanded: Boolean = false,
        val isWaterNeedsDropdownExpanded: Boolean = false,
    )

    init {
        screenModelScope.launch {
            repository.getAllCropsAsFlow().collect { crops ->
                mutableState.update { state ->
                    state.copy(
                        crops = crops
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

    fun updateSeason(season: String) {
        mutableState.update { state ->
            state.copy(seasonEn = season, isSeasonDropdownExpanded = false)
        }
    }

    fun updateSoilType(soilType: String) {
        mutableState.update { state ->
            state.copy(soilType = soilType, isSoilDropdownExpanded = false)
        }
    }

    fun updateWaterNeeds(waterNeeds: String) {
        mutableState.update { state ->
            state.copy(waterNeeds = waterNeeds, isWaterNeedsDropdownExpanded = false)
        }
    }

    fun syncLocalDatabaseFromCloud() {
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

    private fun filterCrops(
        crops: List<Crops>,
        query: String?
    ): List<Crops> {
        if (query.isNullOrEmpty()) return crops
        return crops.filter { crop ->
            crop.name.contains(query, ignoreCase = true) || crop.nameBn.contains(query, ignoreCase = true)
        }
    }
}