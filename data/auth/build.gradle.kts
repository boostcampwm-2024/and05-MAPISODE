plugins {
	alias(libs.plugins.mapisode.data)
}

android {
	namespace = "com.boostcamp.mapisode.auth"
}

dependencies {
	implementation(projects.core.datastore)
	implementation(projects.domain.auth)
}
