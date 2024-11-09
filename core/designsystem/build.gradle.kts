plugins {
	alias(libs.plugins.mapisode.android.compose)
	alias(libs.plugins.compose.compiler)
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
	androidTestImplementation(libs.bundles.test)
}
