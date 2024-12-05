package com.example.agrilearninghub.di

import com.example.agrilearninghub.data.CropsRepositoryImpl
import com.example.agrilearninghub.domain.repository.CropsRepository
import com.example.agrilearninghub.ui.detail.DetailScreenModel
import com.example.agrilearninghub.ui.index.IndexScreenModel
import com.example.agrilearninghub.ui.more.RecommendationScreenModel
import org.koin.dsl.module

val domainModule =
    module {
        single<CropsRepository> { CropsRepositoryImpl(db = get()) }

        factory { IndexScreenModel(repository = get()) }
        factory { (cropId: Long) -> DetailScreenModel(cropId = cropId, repository = get()) }
        factory { RecommendationScreenModel(repository = get()) }
    }