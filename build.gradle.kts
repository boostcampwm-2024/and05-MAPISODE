plugins {
	alias(libs.plugins.android.application) apply false
	alias(libs.plugins.kotlin.android) apply false
	alias(libs.plugins.kotlin.jvm) apply false
	alias(libs.plugins.kotlin.serialization) apply false
	alias(libs.plugins.compose.compiler) apply false
	alias(libs.plugins.hilt) apply false
	alias(libs.plugins.ksp) apply false
	alias(libs.plugins.android.library) apply false
	alias(libs.plugins.ktlint) apply false
	id("com.google.gms.google-services") version "4.4.2" apply false
}

buildscript {
	repositories {
		google()
		mavenCentral()
		maven {
			url = uri("https://repository.map.naver.com/archive/maven")
		}
	}
}

allprojects {
	apply {
		plugin(rootProject.libs.plugins.ktlint.get().pluginId)
	}
}

apply {
	from("gradle/projectDependencyGraph.gradle")
}
