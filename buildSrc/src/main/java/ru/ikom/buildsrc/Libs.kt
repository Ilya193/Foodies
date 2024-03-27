package ru.ikom.buildsrc

object Libs {
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleRuntimeKtx}"
    const val activityCompose = "androidx.activity:activity-compose:${Versions.activityCompose}"
    const val composeBom = "androidx.compose:compose-bom:${Versions.composeBom}"
    const val composeUi = "androidx.compose.ui:ui"
    const val composeGraphics = "androidx.compose.ui:ui-graphics"
    const val composeToolingPreview = "androidx.compose.ui:ui-tooling-preview"
    const val composeMaterial3 = "androidx.compose.material3:material3"
    const val navigationCompose = "androidx.navigation:navigation-compose:${Versions.navigationCompose}"
    const val constraintLayoutCompose = "androidx.constraintlayout:constraintlayout-compose:1.0.1"
    const val koin = "io.insert-koin:koin-androidx-compose:${Versions.koin}"
    const val lottie = "com.airbnb.android:lottie-compose:${Versions.lottie}"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val kotlinxSerialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kotlinxSerialization}"
}

object Versions {
    const val coreKtx = "1.12.0"
    const val lifecycleRuntimeKtx = "2.7.0"
    const val activityCompose = "1.8.2"
    const val composeBom = "2024.03.00"
    const val navigationCompose = "2.7.7"
    const val koin = "3.4.1"
    const val lottie = "6.4.0"
    const val gson = "2.10.1"
    const val kotlinxSerialization = "1.6.3"
}