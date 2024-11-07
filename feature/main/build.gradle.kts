plugins {
	alias(libs.plugins.mapisode.feature)
}

android {
	namespace = "com.boostcamp.mapisode.main"
}

dependencies {
	implementation(projects.feature.home)
	implementation(projects.feature.episode)
	implementation(projects.feature.mypage)
	implementation(projects.feature.mygroup)
	implementation(libs.kotlinx.immutable)
}
