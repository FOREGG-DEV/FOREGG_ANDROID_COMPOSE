import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

val localProps = Properties().apply {
    val f = rootProject.file("local.properties")
    if (f.exists()) load(FileInputStream(f))
}

android {
    namespace = "com.hugg.main"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        val kakao = localProps.getProperty("kakao_native_key")
        buildConfigField("String", "KAKAO_NATIVE_KEY", "\"$kakao\"")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
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

    implementation(project(":feature"))
    implementation(project(":feature:sign"))
    implementation(project(":feature:home"))
    implementation(project(":feature:calendar"))
    implementation(project(":feature:account"))
    implementation(project(":feature:dailyHugg"))
    implementation(project(":feature:myPage"))
    implementation(project(":feature:challenge"))
    implementation(project(":feature:challenge:list"))
    implementation(project(":feature:challenge:create"))
    implementation(project(":feature:challenge:support"))
    implementation(project(":feature:notification"))

    implementation(AndroidX.CORE)
    implementation(AndroidX.APPCOMPAT)
    implementation(Google.MATERIAL)
    implementation(AndroidX.NAVIGATION_COMPOSE)
    implementation(AndroidX.LIFECYCLE_VIEW_MODEL_KTX)
    implementation(AndroidX.LIFECYCLE_RUNTIME_KTX)
    implementation(AndroidX.LIVE_DATA)
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


    //Lottie
    implementation(Libraries.LOTTIE)
    implementation(Libraries.COIL_SVG)
    implementation(Libraries.COIL)

    implementation(Google.FCM)
    implementation(Google.FCM_KTX)
    implementation(Google.FIREBASE_ANALYTICS)
    implementation(platform(Google.FIREBASE_BOM))
}