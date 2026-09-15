plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.ksp) //Unresolved reference to version catalog
    alias(libs.plugins.hilt.android)
}

android {
    namespace = "com.nikhilkhairnar.goodsam"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.nikhilkhairnar.goodsam"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    // Navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.fragment)

    // Retrofit / OkHttp / Gson
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp.core)
    implementation(libs.okhttp.logging)
    implementation(libs.gson)

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)

    // Glide
    implementation(libs.glide)
    ksp(libs.glide.compiler)

    // DataStore
    implementation(libs.androidx.datastore.preferences)

    // OKHttp
    implementation(libs.okhttp.logging)
}