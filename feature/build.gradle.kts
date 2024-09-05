plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "com.hugg.feature"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
        kotlinCompilerExtensionVersion = "1.5.14"
    }

    packagingOptions {
        exclude("META-INF/gradle/incremental.annotation.processors")
    }
}

dependencies {
    implementation(project(":domain"))

    implementation(AndroidX.NAVIGATION_COMPOSE)
    implementation(AndroidX.CORE)
    implementation(AndroidX.LIFECYCLE_RUNTIME_KTX)
    implementation(AndroidX.ACTIVITY_COMPOSE)
    implementation(platform(AndroidX.COMPOSE_BOM))
    implementation(AndroidX.COMPOSE_UI)
    implementation(AndroidX.COMPOSE_GRAPHICS)
    implementation(AndroidX.COMPOSE_PREVIEW)
    implementation(AndroidX.COMPOSE_MATERIAL)

    implementation(AndroidX.THREE_TEN)
    debugImplementation("androidx.compose.ui:ui-tooling:1.6.8")

    //힐트
    implementation(Google.HILT_ANDROID)
    implementation(Google.HILT_CORE)
    implementation(Google.HILT_COMPOSE)
    kapt(Google.HILT_COMPILER)
}