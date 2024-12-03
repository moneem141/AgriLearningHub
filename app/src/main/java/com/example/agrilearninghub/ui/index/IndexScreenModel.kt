package com.example.agrilearninghub.ui.index

import cafe.adriel.voyager.core.model.StateScreenModel
import com.example.agrilearninghub.data.Crops

class IndexScreenModel : StateScreenModel<IndexScreenModel.State>(State()) {
    data class State(
        val crops: List<Crops> = emptyList(),
        val isLoading: Boolean = true,
        val searchQuery: String? = null
    )

    fun syncLocalDatabaseFromCloud() {
    }
}