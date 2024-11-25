plugins {
	alias(libs.plugins.mapisode.android.library)
}

android {
	namespace = "com.boostcamp.mapisode.common"
}

dependencies {
	implementation(projects.core.model)
	implementation(libs.bundles.naverMap)
}
