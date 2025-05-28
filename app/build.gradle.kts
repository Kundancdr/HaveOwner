plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("plugin.serialization") version "2.1.0"
}

android {
    namespace = "com.example.haveneraowner"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.haveneraowner"
        minSdk = 26
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation("androidx.compose.material:material:1.7.5")

    val navVersion = "2.7.6"

    // Navigation
    implementation("androidx.navigation:navigation-compose:$navVersion")
    // extended icon
    implementation("androidx.compose.material:material-icons-extended:1.6.8")
    //lottie
    implementation( "com.airbnb.android:lottie-compose:5.2.0")
    //koin
    implementation( "io.insert-koin:koin-android:4.0.1")
    implementation ("io.insert-koin:koin-androidx-compose:4.0.1")
    implementation ("io.insert-koin:koin-core:4.0.1")
    // Coil
    implementation ("io.coil-kt:coil-compose:2.5.0")
    implementation("io.coil-kt.coil3:coil-compose:3.0.4")
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.0.4")
    //Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.3")
    // preferences
    implementation("androidx.datastore:datastore-preferences:1.1.1")
    //location
     implementation ("androidx.activity:activity-compose:1.8.0")
    // Google Maps
    implementation("com.google.maps.android:maps-compose:2.11.4")
    // Places SDK
    implementation("com.google.android.libraries.places:places:3.4.0")


    //Graph
    //implementation("com.github.tehras:charts:0.2.4")
    // RazorPay
  //  implementation ("com.razorpay:checkout:1.6.40")
    //Google map SDK
//    implementation ("com.google.android.gms:play-services-maps:19.0.0")
//    implementation ("com.google.maps.android:maps-compose:4.3.3")
//    implementation("com.google.android.libraries.places:places:4.1.0")
//    implementation ("com.google.android.gms:play-services-location:21.0.1")
    //timber for location
//    implementation ("com.jakewharton.timber:timber:5.0.1")
}