package com.example.agrilearninghub

import android.app.Application
import com.example.agrilearninghub.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.androix.startup.KoinStartup
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.KoinAppDeclaration

@OptIn(KoinExperimentalAPI::class)
class App : Application(), KoinStartup {
    override fun onKoinStartup(): KoinAppDeclaration =
        {
            androidContext(this@App)
            modules(appModule)
        }
}