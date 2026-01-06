import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
}

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localPropertiesFile.inputStream().use { stream ->
        localProperties.load(stream)
    }
}

android {
    namespace = "com.kelvin.apptraveling"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.kelvin.apptraveling"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        val mapsApiKey = localProperties.getProperty("GOOGLE_MAPS_API_KEY") ?: ""
        manifestPlaceholders["GOOGLE_MAPS_API_KEY"] = mapsApiKey
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
        viewBinding = true
        dataBinding = true
    }
}

dependencies {

    // Room
    implementation("androidx.room:room-runtime:2.5.2")
    annotationProcessor("androidx.room:room-compiler:2.5.2")

    implementation("com.google.android.gms:play-services-maps:19.2.0")
    implementation("androidx.fragment:fragment:1.6.2")

    implementation(libs.glide) // Para cargar imagenes
    implementation(libs.glide.v4132)
    implementation(libs.retrofit)
    implementation(libs.converter.gson) // Libreria para traducir de json a objetos java
    implementation(libs.shimmer)
    implementation(libs.recyclerview)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}