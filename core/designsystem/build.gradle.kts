plugins {
	alias(libs.plugins.mapisode.android.compose)
}

android {
	namespace = "com.boostcamp.mapisode.designsystem"
	defaultConfig {
		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}
}

dependencies {
	implementation(projects.core.ui)
	implementation(projects.core.model)
	implementation(libs.bundles.coil)
	androidTestImplementation(libs.bundles.test)
}
