plugins {
	alias(libs.plugins.mapisode.feature)
}

android {
	namespace = "com.boostcamp.mapisode.mypage"
}

dependencies {
	implementation(libs.bundles.coil)
	implementation(libs.androidx.browser)
	implementation(projects.core.auth)
	implementation(projects.domain.user)
}
