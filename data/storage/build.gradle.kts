plugins {
	alias(libs.plugins.mapisode.data)
}

android {
	namespace = "com.boostcamp.mapisode.storage"
}

dependencies {
	implementation(projects.core.firebase)
	implementation(projects.domain.storage)
	implementation(platform(libs.firebase.bom))
	implementation(libs.firebase.storage)
}
