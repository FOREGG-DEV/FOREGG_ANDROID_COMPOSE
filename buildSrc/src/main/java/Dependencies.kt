object Kotlin {
    const val KOTLIN_STDLIB = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.K_STDLIB}"
    const val COROUTINES_CORE =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.K_COROUTINES_CORE}"
    const val COROUTINES = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.K_COROUTINES}"
}

object Google {
    const val MATERIAL = "com.google.android.material:material:${Versions.MATERIAL}"
    const val HILT_CORE = "com.google.dagger:hilt-core:${Versions.HILT}"
    const val HILT_COMPILER = "com.google.dagger:hilt-compiler:${Versions.HILT}"
    const val HILT_ANDROID = "com.google.dagger:hilt-android:${Versions.HILT}"
    const val GLIDE = "com.github.bumptech.glide:glide:${Versions.GLIDE}"
    const val FCM = "com.google.firebase:firebase-messaging:${Versions.FCM}"
    const val FCM_KTX = "com.google.firebase:firebase-messaging-ktx:${Versions.FCM}"
    const val FIREBASE_BOM = "com.google.firebase:firebase-bom:${Versions.FIREBASE}"
    const val FIREBASE_ANALYTICS = "com.google.firebase:firebase-analytics-ktx"
}

object AndroidX{
    const val CORE = "androidx.core:core-ktx:${Versions.CORE}"
    const val APPCOMPAT = "androidx.appcompat:appcompat:${Versions.APPCOMPAT}"
    const val LIFECYCLE_RUNTIME_KTX =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.LIFECYCLE_KTX}"
    const val LIFECYCLE_VIEW_MODEL_KTX =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.VIEW_MODEL_KTX}"
    const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINT_LAYOUT}"
    const val DATA_STORE_PROTO = "androidx.datastore:datastore:${Versions.DATA_STORE}"
    const val DATA_STORE_PREFERENCES = "androidx.datastore:datastore-preferences:${Versions.DATA_STORE}"
    const val FRAGMENT_KTX = "androidx.fragment:fragment-ktx:${Versions.FRAGMENT_KTX}"
    const val NAVIGATION_COMPOSE = "androidx.navigation:navigation-compose:${Versions.NAVIGATION}"
    const val SPLASH = "androidx.core:core-splashscreen:1.0.0"
    const val THREE_TEN = "com.jakewharton.threetenabp:threetenabp:1.3.1"
    const val SWIPE_REFRESH = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.REFRESH}"
    const val ACTIVITY_COMPOSE = "androidx.activity:activity-compose:${Versions.ACTIVITY_COMPOSE}"
    const val COMPOSE_BOM = "androidx.compose:compose-bom:${Versions.COMPOSE_BOM}"
    const val COMPOSE_UI = "androidx.compose.ui:ui"
    const val COMPOSE_GRAPHICS = "androidx.compose.ui:ui-graphics"
    const val COMPOSE_PREVIEW = "androidx.compose.ui:ui-tooling-preview"
    const val COMPOSE_MATERIAL = "androidx.compose.material3:material3"
}

object Libraries {
    const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
    const val RETROFIT_CONVERTER_GSON = "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT}"
    const val OKHTTP = "com.squareup.okhttp3:okhttp:${Versions.OKHTTP}"
    const val OKHTTP_LOGGING_INTERCEPTOR =
        "com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP}"
    const val VIEWPAGER_INDICATOR = "com.tbuonomo:dotsindicator:${Versions.VIEW_PAGER_INDICATOR}"
    const val LOTTIE = "com.airbnb.android:lottie:${Versions.LOTTIE}"
    const val COIL = "io.coil-kt:coil:${Versions.COIL}"
    const val COIL_SVG = "io.coil-kt:coil-svg:${Versions.COIL}"
    const val FLEX_BOX = "com.google.android.flexbox:flexbox:${Versions.FLEX_BOX}"
}

object KAKAO{
    const val AUTH = "com.kakao.sdk:v2-user:${Versions.KAKAO}"
    const val SHARE = "com.kakao.sdk:v2-share:${Versions.KAKAO}"
}
