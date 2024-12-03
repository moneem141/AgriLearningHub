package com.example.agrilearninghub.di

import android.os.Build
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.agrilearninghub.data.AppDatabase
import io.requery.android.database.sqlite.RequerySQLiteOpenHelperFactory
import org.koin.dsl.module

val appModule =
    module {
        single<SqlDriver> {
            AndroidSqliteDriver(
                schema = AppDatabase.Schema,
                context = get(),
                name = "agrilearn.db",
                factory =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        FrameworkSQLiteOpenHelperFactory()
                    } else {
                        RequerySQLiteOpenHelperFactory()
                    },
                callback =
                    object : AndroidSqliteDriver.Callback(AppDatabase.Schema) {
                        override fun onOpen(db: SupportSQLiteDatabase) {
                            db.setForeignKeyConstraintsEnabled(true)
                        }
                    }
            )
        }

        single { AppDatabase(driver = get()) }
    }