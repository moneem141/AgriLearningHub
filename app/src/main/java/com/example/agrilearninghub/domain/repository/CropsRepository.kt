package com.example.agrilearninghub.domain.repository

import com.example.agrilearninghub.data.Crops
import kotlinx.coroutines.flow.Flow

interface CropsRepository {
    suspend fun getCropById(id: Long): Crops

    fun getCropByIdAsFlow(id: Long): Flow<Crops>

    suspend fun getAllCrops(): List<Crops>

    fun getAllCropsAsFlow(): Flow<List<Crops>>

    suspend fun insertCrop(crop: Crops)

    suspend fun updateCrop(
        id: Long,
        crop: Crops
    )

    suspend fun syncLocalDatabaseFromCloud()
}