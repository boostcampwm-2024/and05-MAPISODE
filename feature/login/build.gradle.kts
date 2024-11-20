plugins {
	alias(libs.plugins.mapisode.feature)
}

android {
	namespace = "com.boostcamp.mapisode.login"
}

dependencies {
	implementation(projects.domain.auth)
	implementation(platform(libs.firebase.bom))
	implementation(libs.bundles.firebase)
	implementation(libs.google.play.services.auth)
	implementation(libs.androidx.credential)
	implementation(libs.googleid.identity)
}
