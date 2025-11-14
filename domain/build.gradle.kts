plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(Kotlin.KOTLIN_STDLIB)
    implementation(Kotlin.COROUTINES_CORE)

    implementation(Google.HILT_CORE)

    implementation(AndroidX.THREE_TEN)

    //RETROFIT
    implementation(Libraries.RETROFIT)
    implementation(Libraries.RETROFIT_CONVERTER_GSON)
}