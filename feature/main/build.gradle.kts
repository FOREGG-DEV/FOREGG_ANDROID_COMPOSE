plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.hugg.main"
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
    implementation(project(":data"))
    implementation(project(":domain"))

    implementation(project(":feature"))
    implementation(project(":feature:sign"))

    implementation(AndroidX.CORE)
    implementation(AndroidX.APPCOMPAT)
    implementation(Google.MATERIAL)
    implementation(AndroidX.NAVIGATION_COMPOSE)
    implementation(AndroidX.LIFECYCLE_VIEW_MODEL_KTX)
    implementation(AndroidX.LIFECYCLE_RUNTIME_KTX)
    implementation(AndroidX.SPLASH)
    implementation(AndroidX.THREE_TEN)
    implementation(AndroidX.SWIPE_REFRESH)
    implementation(AndroidX.DATA_STORE_PREFERENCES)

    //코루틴
    implementation(Kotlin.COROUTINES_CORE)
    implementation(Kotlin.COROUTINES)

    //힐트
    implementation(Google.HILT_ANDROID)
    implementation(Google.HILT_CORE)
    implementation(Google.FCM)
    implementation(Google.FCM_KTX)
    implementation(Google.FIREBASE_ANALYTICS)
    implementation(platform(Google.FIREBASE_BOM))

    implementation(AndroidX.COMPOSE_BOM)
    implementation(AndroidX.COMPOSE_MATERIAL)
    implementation(AndroidX.COMPOSE_UI)
    implementation(AndroidX.COMPOSE_GRAPHICS)
    implementation(AndroidX.COMPOSE_PREVIEW)
    implementation(AndroidX.ACTIVITY_COMPOSE)



    kapt(Google.HILT_COMPILER)

    implementation(KAKAO.AUTH)
    implementation(KAKAO.SHARE)
    implementation(Google.GLIDE)

    implementation(Libraries.VIEWPAGER_INDICATOR)
    implementation(Libraries.FLEX_BOX)

    //Lottie
    implementation(Libraries.LOTTIE)
    implementation(Libraries.COIL_SVG)
    implementation(Libraries.COIL)
}