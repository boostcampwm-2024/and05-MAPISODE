plugins {
	alias(libs.plugins.mapisode.android.library)
	alias(libs.plugins.mapisode.android.hilt)
	alias(libs.plugins.kotlin.serialization)
}

android {
	namespace = "com.boostcamp.mapisode.network"
}

dependencies {
	implementation(platform(libs.okhttp.bom))
	implementation(platform(libs.retrofit.bom))
	implementation(projects.core.datastore)
	implementation(projects.core.model)
	implementation(libs.bundles.retrofit)
	implementation(libs.bundles.okhttp)
	implementation(libs.kotlinx.serialization.json)
}
