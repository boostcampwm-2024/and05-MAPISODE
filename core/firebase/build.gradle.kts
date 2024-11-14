plugins {
	alias(libs.plugins.mapisode.android.library)
}

android {
	namespace = "com.boostcamp.mapisode.firebase"
}

dependencies {
	implementation(platform(libs.firebase.bom))
	implementation(libs.bundles.firebase)
}
