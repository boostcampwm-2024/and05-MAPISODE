plugins {
	alias(libs.plugins.mapisode.android.application)
	alias(libs.plugins.mapisode.android.hilt)
}

android {
	namespace = "com.boostcamp.mapisode"

	defaultConfig {
		applicationId = "com.boostcamp.mapisode"
		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}

	packaging {
		resources {
			excludes += "/META-INF/{AL2.0,LGPL2.1}"
		}
	}

	buildFeatures {
		buildConfig = true
	}
}

dependencies {
	implementation(projects.core.ui)
	implementation(projects.core.designsystem)
	implementation(projects.core.navigation)
	implementation(projects.feature.main)
}
