package com.example.agrilearninghub.data

import android.util.Log
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import com.example.agrilearninghub.domain.repository.CropsRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject

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
        db.cropsQueries.transaction {
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
    }

    override suspend fun updateCrop(
        id: Long,
        crop: Crops
    ) {
        db.cropsQueries.transaction {
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

    override suspend fun syncLocalDatabaseFromCloud() {
        val firestore = FirebaseFirestore.getInstance()
        val json = Json { ignoreUnknownKeys = true }
        try {
            val snapshot = firestore.collection("crops").get().await()
            val cloudCrops =
                snapshot.documents.mapNotNull { document ->
                    try {
                        val jsonObject =
                            buildJsonObject {
                                document.data?.forEach { (key, value) ->
                                    put(key, JsonPrimitive(value.toString()))
                                }
                            }
                        val jsonString = jsonObject.toString()
                        json.decodeFromString<FirestoreCrops>(jsonString)
                    } catch (e: Exception) {
                        Log.e("CropsRepositoryImpl", "Error deserializing crop: ${document.data}", e)
                        null
                    }
                }

            val localCrops = db.cropsQueries.getAllCrops().executeAsList()

            db.cropsQueries.transaction {
                // Insert or update crops
                cloudCrops.forEach { cloudCrop ->
                    val existingCrop = localCrops.find { it.name == cloudCrop.name }
                    if (existingCrop == null) {
                        db.cropsQueries.insertCrop(
                            name = cloudCrop.name,
                            nameBn = cloudCrop.nameBn,
                            image = cloudCrop.image,
                            scientificName = cloudCrop.scientificName,
                            family = cloudCrop.family,
                            description = cloudCrop.description,
                            season = cloudCrop.season,
                            climate = cloudCrop.climate,
                            soil = cloudCrop.soil,
                            seed = cloudCrop.seed,
                            planting = cloudCrop.planting,
                            fertilization = cloudCrop.fertilization,
                            irrigation = cloudCrop.irrigation,
                            care = cloudCrop.care,
                            harvestTime = cloudCrop.harvestTime,
                            market = cloudCrop.market,
                            nutrition = cloudCrop.nutrition,
                            uses = cloudCrop.uses,
                            commonDiseases = cloudCrop.commonDiseases,
                            solutions = cloudCrop.solutions
                        )
                    } else {
                        db.cropsQueries.updateCrop(
                            id = existingCrop.id,
                            name = cloudCrop.name,
                            nameBn = cloudCrop.nameBn,
                            image = cloudCrop.image,
                            scientificName = cloudCrop.scientificName,
                            family = cloudCrop.family,
                            description = cloudCrop.description,
                            season = cloudCrop.season,
                            climate = cloudCrop.climate,
                            soil = cloudCrop.soil,
                            seed = cloudCrop.seed,
                            planting = cloudCrop.planting,
                            fertilization = cloudCrop.fertilization,
                            irrigation = cloudCrop.irrigation,
                            care = cloudCrop.care,
                            harvestTime = cloudCrop.harvestTime,
                            market = cloudCrop.market,
                            nutrition = cloudCrop.nutrition,
                            uses = cloudCrop.uses,
                            commonDiseases = cloudCrop.commonDiseases,
                            solutions = cloudCrop.solutions
                        )
                    }
                }

                localCrops.forEach { localCrop ->
                    if (cloudCrops.none { it.name == localCrop.name }) {
                        db.cropsQueries.deleteCrop(localCrop.id)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("CropsRepositoryImpl", "Error syncing crops from cloud", e)
        }
    }
}