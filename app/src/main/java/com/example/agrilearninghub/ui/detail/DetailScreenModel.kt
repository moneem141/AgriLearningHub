package com.example.agrilearninghub.ui.detail

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.agrilearninghub.data.Crops
import com.example.agrilearninghub.domain.repository.CropsRepository
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailScreenModel(
    private val cropId: Long,
    private val repository: CropsRepository
) : StateScreenModel<DetailScreenModel.State>(State()) {
    data class State(
        val crop: Crops =
            Crops(
                id = 0,
                name = "",
                nameBn = "",
                scientificName = "",
                family = "",
                description = "",
                season = "",
                climate = "",
                soil = "",
                seed = "",
                planting = "",
                fertilization = "",
                irrigation = "",
                care = "",
                harvestTime = "",
                market = "",
                nutrition = "",
                uses = "",
                commonDiseases = "",
                solutions = "",
                image = ""
            )
    )

    init {
        screenModelScope.launch {
            mutableState.update { state ->
                state.copy(
                    crop = repository.getCropById(cropId)
                )
            }
        }
    }
}