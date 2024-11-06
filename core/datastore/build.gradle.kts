plugins {
	alias(libs.plugins.mapisode.android.library)
	alias(libs.plugins.mapisode.android.hilt)
	alias(libs.plugins.kotlin.serialization)
}

android {
	namespace = "com.boostcamp.mapisode.datastore"
}

dependencies {
	implementation(libs.kotlinx.serialization.json)
	implementation(libs.bundles.datastore)
}
