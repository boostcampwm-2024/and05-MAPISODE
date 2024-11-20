import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
	alias(libs.plugins.mapisode.feature)
}

android {
	namespace = "com.boostcamp.mapisode.login"

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
	implementation(projects.domain.auth)
	implementation(platform(libs.firebase.bom))
	implementation(libs.bundles.firebase)
	implementation(libs.google.play.services.auth)
	implementation(libs.androidx.credential)
	implementation(libs.googleid.identity)
}
