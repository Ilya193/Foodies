plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "ru.ikom.feature_basket"
    compileSdk = 34

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }

    kotlinOptions {
        jvmTarget = "1.8"
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

    implementation(project(":common"))

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation(ru.ikom.buildsrc.Libs.navigationCompose)
    implementation(ru.ikom.buildsrc.Libs.koin)

    implementation(ru.ikom.buildsrc.Libs.constraintLayoutCompose)
}