plugins {
	alias(libs.plugins.mapisode.data)
}

android {
	namespace = "com.boostcamp.mapisode.mygroup"
}

dependencies {
	implementation(projects.core.model)
	implementation(projects.domain.mygroup)
	implementation(projects.core.firebase)
	implementation(platform(libs.firebase.bom))
	implementation(libs.firebase.firestore)
}
