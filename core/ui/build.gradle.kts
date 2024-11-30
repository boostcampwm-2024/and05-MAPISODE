plugins {
	alias(libs.plugins.mapisode.android.compose)
	alias(libs.plugins.kotlin.parcelize)
}

android {
	namespace = "com.boostcamp.mapisode.ui"
}

dependencies {
	implementation(projects.core.model)
	implementation(projects.core.designsystem)
	implementation(libs.bundles.coil)
}
