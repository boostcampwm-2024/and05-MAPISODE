plugins {
	alias(libs.plugins.mapisode.data)
}

android {
	namespace = "com.boostcamp.mapisode.user"
}

dependencies {
	implementation(projects.domain.user)
	implementation(projects.core.firebase)
	implementation(platform(libs.firebase.bom))
	implementation(libs.firebase.firestore)
	implementation(libs.firebase.storage)
}
