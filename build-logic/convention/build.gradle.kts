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
            implementationClass = "AndroidApplicationPlugin"
        }

        register("androidLibrary") {
            id = "mapisode.android.library"
            implementationClass = "AndroidLibraryPlugin"
        }

        register("androidCompose") {
            id = "mapisode.android.compose"
            implementationClass = "AndroidComposePlugin"
        }

        register("androidHilt") {
            id = "mapisode.android.hilt"
            implementationClass = "HiltPlugin"
        }

        register("feature") {
            id = "mapisode.feature"
            implementationClass = "MapisodeFeaturePlugin"
        }

        register("data") {
            id = "mapisode.data"
            implementationClass = "MapisodeDataPlugin"
        }
    }
}
