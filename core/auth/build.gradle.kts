import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
	alias(libs.plugins.mapisode.android.library)
}

android {
	namespace = "com.boostcamp.mapisode.auth"
	defaultConfig {
		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
		val googleWebClientId =
			gradleLocalProperties(rootDir, providers).getProperty("GOOGLE_WEB_CLIENT_ID") ?: ""
		if (googleWebClientId.isEmpty()) {
			throw GradleException("GOOGLE_WEB_CLIENT_ID is not set.")
		}
		buildConfigField("String", "GOOGLE_WEB_CLIENT_ID", "\"$googleWebClientId\"")
		manifestPlaceholders["GOOGLE_WEB_CLIENT_ID"] = googleWebClientId
	}
	buildFeatures {
		buildConfig = true
	}
}

dependencies {
	implementation(projects.core.model)
	implementation(libs.kotlinx.coroutines.core)
	implementation(platform(libs.firebase.bom))
	implementation(libs.firebase.auth)
	implementation(libs.bundles.auth)
}
