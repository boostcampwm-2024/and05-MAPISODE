package com.boostcamp.mapisode.convention

import com.boostcamp.mapisode.convention.extension.getBundle
import com.boostcamp.mapisode.convention.extension.implementation
import com.boostcamp.mapisode.convention.extension.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class MapisodeFeaturePlugin : Plugin<Project> {
	override fun apply(target: Project) {
		with(target) {
			pluginManager.apply {
				apply("com.android.library")
				apply("mapisode.android.hilt")
				apply("mapisode.android.compose")
			}

			dependencies {
				implementation(project(":core:ui"))
				implementation(project(":core:designsystem"))
				implementation(project(":core:model"))
				implementation(project(":core:navigation"))
				implementation(project(":core:datastore"))
				implementation(project(":core:common"))
				implementation(libs.getBundle("compose"))
				implementation(libs.getBundle("navigation"))
			}
		}
	}
}
