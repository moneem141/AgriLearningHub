package com.example.agrilearninghub.data

import kotlinx.serialization.Serializable

@Serializable
data class FirestoreCrops(
    val id: Long = 0,
    val name: String = "",
    val nameBn: String = "",
    val image: String = "",
    val scientificName: String = "",
    val family: String = "",
    val description: String = "",
    val season: String = "",
    val climate: String = "",
    val soil: String = "",
    val seed: String = "",
    val planting: String = "",
    val fertilization: String = "",
    val irrigation: String = "",
    val care: String = "",
    val harvestTime: String = "",
    val market: String = "",
    val nutrition: String = "",
    val uses: String = "",
    val commonDiseases: String = "",
    val solutions: String = "",
    val tempLow: Long = 0,
    val tempHigh: Long = 0,
    val phLow: Double = 0.0,
    val phHigh: Double = 0.0,
    val seasonEn: String = "",
    val soilType: String = "",
    val waterNeeds: String = ""
)