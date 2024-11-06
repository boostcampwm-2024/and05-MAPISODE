plugins {
	alias(libs.plugins.mapisode.android.compose)
}

android {
	namespace = "com.boostcamp.mapisode.designsystem"
}

dependencies {
	implementation(projects.core.ui)
	implementation(projects.core.model)
}
