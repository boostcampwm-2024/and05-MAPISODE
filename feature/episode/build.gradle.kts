plugins {
	alias(libs.plugins.mapisode.feature)
}

android {
	namespace = "com.boostcamp.mapisode.episode"
}

dependencies {
	implementation(project.libs.bundles.naverMap)
	implementation(projects.core.ui)
	implementation(projects.core.designsystem)
}
