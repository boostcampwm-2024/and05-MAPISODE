import com.boostcamp.mapisode.convention.extension.implementation

plugins {
	alias(libs.plugins.mapisode.feature)
}

android {
	namespace = "com.boostcamp.mapisode.home"
}

dependencies {
	implementation(projects.domain.episode)
	implementation(projects.domain.user)
	implementation(projects.domain.mygroup)
	implementation(projects.core.network)
	implementation(libs.bundles.naverMap)
	implementation(libs.bundles.coil)
}
