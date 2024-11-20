plugins {
	alias(libs.plugins.mapisode.java.library)
}

dependencies {
	implementation(projects.core.model)
	implementation(libs.kotlinx.coroutines.core)
}
