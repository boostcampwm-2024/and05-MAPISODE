plugins {
	`kotlin-dsl`
}

group = "com.boostcamp.mapisode.convention"

java {
	sourceCompatibility = JavaVersion.VERSION_17
	targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
	compileOnly(libs.android.gradlePlugin)
	compileOnly(libs.kotlin.gradlePlugin)
	compileOnly(libs.compose.compiler.gradlePlugin)
	compileOnly(libs.ksp.gradlePlugin)
}

gradlePlugin {
	plugins {
		register("androidApplication") {
			id = "mapisode.android.application"
			implementationClass = "com.boostcamp.mapisode.convention.AndroidApplicationPlugin"
		}

		register("androidLibrary") {
			id = "mapisode.android.library"
			implementationClass = "com.boostcamp.mapisode.convention.AndroidLibraryPlugin"
		}

		register("androidCompose") {
			id = "mapisode.android.compose"
			implementationClass = "com.boostcamp.mapisode.convention.AndroidComposePlugin"
		}

		register("androidHilt") {
			id = "mapisode.android.hilt"
			implementationClass = "com.boostcamp.mapisode.convention.HiltPlugin"
		}

		register("feature") {
			id = "mapisode.feature"
			implementationClass = "com.boostcamp.mapisode.convention.MapisodeFeaturePlugin"
		}

		register("data") {
			id = "mapisode.data"
			implementationClass = "com.boostcamp.mapisode.convention.MapisodeDataPlugin"
		}

		register("javaLibrary") {
			id = "mapisode.java.library"
			implementationClass = "com.boostcamp.mapisode.convention.JavaLibraryPlugin"
		}
	}
}
