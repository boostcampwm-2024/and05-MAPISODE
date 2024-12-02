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
		maven {
			url = uri("https://repository.map.naver.com/archive/maven")
		}
	}
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
gradle.startParameter.excludedTaskNames.addAll(listOf(":build-logic:convention:testClasses"))

rootProject.name = "Mapisode"
include(":app")
include(":core:auth")
include(":core:network")
include(":core:datastore")
include(":core:designsystem")
include(":core:firebase")
include(":core:ui")
include(":core:common")
include(":core:model")
include(":core:navigation")
include(":feature:home")
include(":feature:episode")
include(":feature:mypage")
include(":feature:mygroup")
include(":feature:main")
include(":feature:login")
include(":domain:episode")
include(":domain:mygroup")
include(":domain:user")
include(":domain:storage")
include(":data:episode")
include(":data:mygroup")
include(":data:user")
include(":data:storage")
