import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
	alias(libs.plugins.mapisode.android.application)
	alias(libs.plugins.mapisode.android.hilt)

	id("com.google.gms.google-services")
}

android {
	namespace = "com.boostcamp.mapisode"

	defaultConfig {
		applicationId = "com.boostcamp.mapisode"
		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
		val naverMapClientId =
			gradleLocalProperties(rootDir, providers).getProperty("NAVER_MAP_CLIENT_ID") ?: ""
		if (naverMapClientId.isEmpty()) {
			throw GradleException("NAVER_MAP_CLIENT_ID is not set.")
		}
		buildConfigField("String", "NAVER_MAP_CLIENT_ID", "\"$naverMapClientId\"")
		manifestPlaceholders["NAVER_MAPS_CLIENT_ID"] = naverMapClientId
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
	implementation(platform(libs.firebase.bom))
	implementation(libs.bundles.firebase)
	implementation(projects.feature.main)

	implementation(projects.data.episode)
	implementation(projects.data.user)
	implementation(projects.data.mygroup)
	implementation(projects.data.storage)
}
