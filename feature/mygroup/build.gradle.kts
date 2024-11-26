import com.boostcamp.mapisode.convention.extension.implementation

plugins {
	alias(libs.plugins.mapisode.feature)
}

android {
	namespace = "com.boostcamp.mapisode.mygroup"
}

dependencies {
	implementation(libs.bundles.coil)
	implementation(projects.domain.mygroup)
	implementation(projects.domain.episode)
	implementation(projects.core.datastore)
}
