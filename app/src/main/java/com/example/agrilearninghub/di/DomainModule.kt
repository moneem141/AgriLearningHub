package com.example.agrilearninghub.di

import com.example.agrilearninghub.data.CropsRepositoryImpl
import com.example.agrilearninghub.domain.repository.CropsRepository
import org.koin.dsl.module

val domainModule =
    module {
        single<CropsRepository> { CropsRepositoryImpl(db = get()) }
    }