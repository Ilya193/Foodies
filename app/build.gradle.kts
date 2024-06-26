plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "ru.ikom.foodies"
    compileSdk = 34

    defaultConfig {
        applicationId = "ru.ikom.foodies"
        minSdk = 21
        targetSdk = 34
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

    implementation(ru.ikom.buildsrc.Libs.coreKtx)
    implementation(ru.ikom.buildsrc.Libs.lifecycleRuntimeKtx)
    implementation(ru.ikom.buildsrc.Libs.activityCompose)
    implementation(platform(ru.ikom.buildsrc.Libs.composeBom))
    implementation(ru.ikom.buildsrc.Libs.composeUi)
    implementation(ru.ikom.buildsrc.Libs.composeGraphics)
    implementation(ru.ikom.buildsrc.Libs.composeToolingPreview)
    implementation(ru.ikom.buildsrc.Libs.composeMaterial3)

    implementation(project(":feature-catalog"))
    implementation(project(":feature-details"))
    implementation(project(":common"))
    implementation(project(":feature-basket"))

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    implementation(ru.ikom.buildsrc.Libs.navigationCompose)
    implementation(ru.ikom.buildsrc.Libs.koin)

    implementation(ru.ikom.buildsrc.Libs.lottie)
}