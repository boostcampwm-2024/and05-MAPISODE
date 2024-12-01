plugins {
	alias(libs.plugins.mapisode.android.library)
	alias(libs.plugins.mapisode.android.compose)
	alias(libs.plugins.kotlin.serialization)
}

android {
	namespace = "com.boostcamp.mapisode.navigation"
}

dependencies {
	implementation(libs.kotlinx.serialization.json)
	implementation(projects.core.model)
}
