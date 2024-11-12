plugins {
	alias(libs.plugins.mapisode.android.library)
	alias(libs.plugins.mapisode.android.hilt)
}

android {
	namespace = "com.boostcamp.mapisode.firebase"
}

dependencies {
	implementation(platform(libs.firebase.bom))
	implementation(libs.bundles.firebase)
}
