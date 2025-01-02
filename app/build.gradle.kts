plugins {
	alias(libs.plugins.android.application)
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