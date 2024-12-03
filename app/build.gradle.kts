plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.spotless)
    alias(libs.plugins.firebase)
    alias(libs.plugins.serialization)
}

android {
    namespace = "com.example.agrilearninghub"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.agrilearninghub"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(jetpack.bundles.jetpack)
    implementation(platform(jetpack.compose.bom))
    implementation(jetpack.bundles.compose)
    implementation(jetpack.bundles.camera)
    implementation(libs.bundles.kotlinx)
    implementation(libs.bundles.sqldelight)
    implementation(platform(libs.koin.bom))
    implementation(libs.bundles.koin)
    implementation(libs.bundles.voyager)
    implementation(platform(libs.coil.bom))
    implementation(libs.bundles.coil)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore)
    implementation(libs.bundles.serialization)

    testImplementation(libs.junit)
    androidTestImplementation(jetpack.bundles.androidTest)
    androidTestImplementation(platform(jetpack.compose.bom))
    debugImplementation(jetpack.bundles.debug)
}

sqldelight {
    databases {
        create("AppDatabase") {
            packageName.set("com.example.agrilearninghub.data")
            dialect(libs.sqldelight.dialects.sql)
            // Additional configuration options if required
            // https://sqldelight.github.io/sqldelight/2.0.2/android_sqlite/gradle/
        }
    }
}

spotless {
    kotlin {
        target("**/*.kt", "**/*.kts")
        targetExclude("**/build/**/*.kt")
        ktlint(
            libs.ktlint.core
                .get()
                .version,
        ).editorConfigOverride(
            mapOf(
                // https://pinterest.github.io/ktlint/latest/rules/code-styles/
                "ktlint_code_style" to "ktlint_official",
                // https://pinterest.github.io/ktlint/latest/rules/standard
                "ktlint_standard_final-newline" to "disabled",
                "ktlint_standard_function-expression-body" to "disabled",
                "ktlint_standard_class-signature" to "disabled",
                "ktlint_function_naming_ignore_when_annotated_with" to "Composable",
                "ktlint_standard_no-trailing-spaces" to "disabled",
                "ktlint_standard_no-unit-return" to "disabled",
                "ktlint_standard_trailing-comma-on-call-site" to "disabled",
                "ktlint_standard_trailing-comma-on-declaration-site" to "disabled",
            )
        )
        trimTrailingWhitespace()
    }
    format("xml") {
        target("**/*.xml")
        trimTrailingWhitespace()
    }
}

tasks.getByName("preBuild").dependsOn(tasks.getByName("spotlessApply"))