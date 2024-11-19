import com.boostcamp.mapisode.convention.extension.implementation

plugins {
	alias(libs.plugins.mapisode.feature)
}

android {
	namespace = "com.boostcamp.mapisode.home"
}

dependencies {
	implementation(libs.bundles.naverMap)
	implementation(libs.bundles.coil)
}
