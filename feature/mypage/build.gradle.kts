import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
	alias(libs.plugins.mapisode.feature)
}

android {
	namespace = "com.boostcamp.mapisode.mypage"

	defaultConfig {
		val privacyPolicy =
			gradleLocalProperties(rootDir, providers).getProperty("PRIVACY_POLICY") ?: ""
		if (privacyPolicy.isEmpty()) {
			throw GradleException("PRIVACY_POLICY is not set.")
		}
		buildConfigField("String", "PRIVACY_POLICY", "\"$privacyPolicy\"")
	}

	buildFeatures {
		buildConfig = true
	}
}

dependencies {
	implementation(libs.bundles.coil)
	implementation(libs.androidx.browser)
	implementation(projects.core.auth)
	implementation(projects.domain.user)
	implementation(projects.domain.storage)
}
