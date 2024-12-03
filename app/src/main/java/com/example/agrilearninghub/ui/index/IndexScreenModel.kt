package com.example.agrilearninghub.ui.index

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.agrilearninghub.data.Crops
import com.example.agrilearninghub.domain.repository.CropsRepository
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class IndexScreenModel(
    private val repository: CropsRepository
) : StateScreenModel<IndexScreenModel.State>(State()) {
    data class State(
        val crops: List<Crops> = emptyList(),
        val isLoading: Boolean = true,
        val searchQuery: String? = null,
        val filteredCrops: List<Crops> = emptyList()
    )

    init {
        screenModelScope.launch {
            repository.getAllCropsAsFlow().collect { crops ->
                mutableState.update { state ->
                    state.copy(
                        crops = crops,
                        filteredCrops = filterCrops(crops, state.searchQuery)
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

    fun syncLocalDatabaseFromCloud() {
        screenModelScope.launch {
            repository.syncLocalDatabaseFromCloud()
            val crops = repository.getAllCrops()
            mutableState.update { state ->
                state.copy(
                    isLoading = false,
                    crops = crops,
                    filteredCrops = filterCrops(crops, state.searchQuery)
                )
            }
        }
    }

    fun updateSearchQuery(query: String?) {
        mutableState.update { state ->
            state.copy(
                searchQuery = query,
                filteredCrops = filterCrops(state.crops, query)
            )
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