package com.example.agrilearninghub.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import com.example.agrilearninghub.domain.repository.CropsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class CropsRepositoryImpl(
    private val db: AppDatabase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : CropsRepository {
    override suspend fun getCropById(id: Long): Crops {
        return db.cropsQueries.getCropById(id).executeAsOne()
    }

    override fun getCropByIdAsFlow(id: Long): Flow<Crops> {
        return db.cropsQueries
            .getCropById(id)
            .asFlow()
            .mapToOne(dispatcher)
    }

    override suspend fun getAllCrops(): List<Crops> {
        return db.cropsQueries.getAllCrops().executeAsList()
    }

    override fun getAllCropsAsFlow(): Flow<List<Crops>> {
        return db.cropsQueries
            .getAllCrops()
            .asFlow()
            .mapToList(dispatcher)
    }

    override suspend fun insertCrop(crop: Crops) {
        db.cropsQueries.insertCrop(
            name = crop.name,
            nameBn = crop.nameBn,
            image = crop.image,
            scientificName = crop.scientificName,
            family = crop.family,
            description = crop.description,
            season = crop.season,
            climate = crop.climate,
            soil = crop.soil,
            seed = crop.seed,
            planting = crop.planting,
            fertilization = crop.fertilization,
            irrigation = crop.irrigation,
            care = crop.care,
            harvestTime = crop.harvestTime,
            market = crop.market,
            nutrition = crop.nutrition,
            uses = crop.uses,
            commonDiseases = crop.commonDiseases,
            solutions = crop.solutions
        )
    }

    override suspend fun updateCrop(
        id: Long,
        crop: Crops
    ) {
        db.cropsQueries.updateCrop(
            id = id,
            name = crop.name,
            nameBn = crop.nameBn,
            image = crop.image,
            scientificName = crop.scientificName,
            family = crop.family,
            description = crop.description,
            season = crop.season,
            climate = crop.climate,
            soil = crop.soil,
            seed = crop.seed,
            planting = crop.planting,
            fertilization = crop.fertilization,
            irrigation = crop.irrigation,
            care = crop.care,
            harvestTime = crop.harvestTime,
            market = crop.market,
            nutrition = crop.nutrition,
            uses = crop.uses,
            commonDiseases = crop.commonDiseases,
            solutions = crop.solutions
        )
    }
}