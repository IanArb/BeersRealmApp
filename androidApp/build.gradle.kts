plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 32
    defaultConfig {
        applicationId = "com.example.beersrealmapp.android"
        minSdk = 21
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.1.1"
    }

    packagingOptions {
        resources.excludes.add("META-INF/*")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}


dependencies {
    implementation("androidx.compose.ui:ui-tooling-preview:${rootProject.extra["compose_version"]}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.1")

    add(
        org.jetbrains.kotlin.gradle.plugin.PLUGIN_CLASSPATH_CONFIGURATION_NAME,
        "androidx.compose.compiler:compiler:1.1.1"
    )

    implementation("androidx.compose.runtime:runtime:1.1.1")

    implementation(project(":shared"))
    implementation("com.google.android.material:material:1.5.0")

    implementation("androidx.compose.ui:ui:1.1.1")
    implementation("androidx.compose.ui:ui-graphics:1.1.1")
    implementation("androidx.compose.ui:ui-tooling:1.1.1")
    implementation("androidx.compose.foundation:foundation-layout:1.1.1")
    implementation("androidx.compose.material:material:1.1.1")
    implementation("androidx.activity:activity-compose:1.4.0")
    implementation("io.coil-kt:coil-compose:1.4.0")

    implementation("com.google.dagger:hilt-android:2.39.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    kapt("com.google.dagger:hilt-android-compiler:2.39.1")
}

kapt {
    correctErrorTypes = true
}