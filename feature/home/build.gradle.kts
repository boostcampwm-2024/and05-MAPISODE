plugins {
	alias(libs.plugins.mapisode.feature)
}

android {
	namespace = "com.boostcamp.mapisode.home"
}

dependencies {
	// TODO : 도메인 참조 변경
	implementation(projects.domain.auth)
}
