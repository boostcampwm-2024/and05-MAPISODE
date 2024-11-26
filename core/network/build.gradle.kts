import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
	alias(libs.plugins.mapisode.android.library)
	alias(libs.plugins.mapisode.android.hilt)
	alias(libs.plugins.kotlin.serialization)
}

android {
	namespace = "com.boostcamp.mapisode.network"

	defaultConfig {
		val naverMapClientId =
			gradleLocalProperties(rootDir, providers).getProperty("NAVER_MAP_CLIENT_ID") ?: ""
		val naverMapClientSecret =
			gradleLocalProperties(rootDir, providers).getProperty("NAVER_MAP_CLIENT_SECRET") ?: ""
		if (naverMapClientId.isEmpty()) {
			throw GradleException("NAVER_MAP_CLIENT_ID is not set.")
		} else if (naverMapClientSecret.isEmpty()) {
			throw GradleException("NAVER_MAP_CLIENT_SECRET is not set.")
		}
		buildConfigField("String", "NAVER_MAP_CLIENT_ID", "\"$naverMapClientId\"")
		buildConfigField("String", "NAVER_MAP_CLIENT_SECRET", "\"$naverMapClientSecret\"")
	}

	buildFeatures {
		buildConfig = true
	}
}

dependencies {
	implementation(platform(libs.okhttp.bom))
	implementation(platform(libs.retrofit.bom))
	implementation(projects.core.datastore)
	implementation(projects.core.model)
	implementation(libs.bundles.retrofit)
	implementation(libs.bundles.okhttp)
	implementation(libs.kotlinx.serialization.json)
}
