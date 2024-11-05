pluginManagement {
	includeBuild("build-logic")

	repositories {
		google {
			content {
				includeGroupByRegex("com\\.android.*")
				includeGroupByRegex("com\\.google.*")
				includeGroupByRegex("androidx.*")
			}
		}
		mavenCentral()
		gradlePluginPortal()
	}
}
dependencyResolutionManagement {
	repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
	repositories {
		google()
		mavenCentral()
	}
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "Mapisode"
include(":app")
include(":core:network")
include(":core:datastore")
include(":core:designsystem")
include(":core:ui")
include(":core:model")
include(":data:auth")
include(":domain:auth")
include(":feature:home")
