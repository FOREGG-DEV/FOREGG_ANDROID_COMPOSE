import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("com.google.gms.google-services")
    id("dagger.hilt.android.plugin")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("com.google.firebase.crashlytics") // ✅ 여기에만 적용
}

android {
    namespace = "com.hugg.presentation"
    compileSdk = 35

    val localPropsFile = rootProject.file("local.properties")
    val localProps = Properties()
    if (localPropsFile.exists()) {
        localProps.load(FileInputStream(localPropsFile))
    }

    defaultConfig {
        buildConfigField("String", "KAKAO_NATIVE_KEY", localProps.getProperty("kakao_native_string_key") ?: "")
        manifestPlaceholders["KAKAO_AOUTH_HOST_SCHEME"] = localProps.getProperty("kakao_native_key") ?: ""
        manifestPlaceholders["Key"] = localProps.getProperty("kakao_native_key") ?: ""
        applicationId = "com.hugg.presentation"
        minSdk = 24
        targetSdk = 35
        versionCode = 11
        versionName = "1.0.7"

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
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }

    packagingOptions {
        exclude("META-INF/gradle/incremental.annotation.processors")
    }
}

dependencies {
    implementation(project(":feature:main"))
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(AndroidX.CORE)
    implementation(AndroidX.APPCOMPAT)
    implementation(Google.MATERIAL)
    implementation(AndroidX.NAVIGATION_COMPOSE)
    implementation(AndroidX.LIFECYCLE_VIEW_MODEL_KTX)
    implementation(AndroidX.LIFECYCLE_RUNTIME_KTX)
    implementation(AndroidX.LIFECYCLE_RUNTIME_COMPOSE)
    implementation(AndroidX.SPLASH)
    implementation(AndroidX.THREE_TEN)
    implementation(AndroidX.SWIPE_REFRESH)
    implementation(AndroidX.DATA_STORE_PREFERENCES)
    implementation(AndroidX.FRAGMENT_KTX)


    //코루틴
    implementation(Kotlin.COROUTINES_CORE)
    implementation(Kotlin.COROUTINES)

    //힐트
    implementation(Google.HILT_ANDROID)
    implementation(Google.HILT_CORE)
    implementation(Google.HILT_COMPOSE)
    implementation(Google.FCM)
    implementation(Google.FCM_KTX)
    implementation(Google.FIREBASE_BOM)
    implementation("com.google.firebase:firebase-crashlytics-ktx:18.3.7")
    implementation("com.google.firebase:firebase-analytics-ktx:21.5.0")

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


    //Lottie
    implementation(Libraries.LOTTIE)
    implementation(Libraries.COIL_SVG)
    implementation(Libraries.COIL)
}
