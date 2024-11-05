package com.boostcamp.mapisode.convention

import com.android.build.gradle.LibraryExtension
import com.boostcamp.mapisode.convention.extension.configureKotlinAndroid
import com.boostcamp.mapisode.convention.extension.configureKotlinCoroutine
import com.boostcamp.mapisode.convention.extension.getLibrary
import com.boostcamp.mapisode.convention.extension.implementation
import com.boostcamp.mapisode.convention.extension.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidLibraryPlugin : Plugin<Project> {
	override fun apply(target: Project) {
		with(target) {
			pluginManager.apply("com.android.library")

			extensions.configure<LibraryExtension> {
				configureKotlinAndroid(this)
				configureKotlinCoroutine(this)
			}

			dependencies {
				implementation(libs.getLibrary("timber"))
			}
		}
	}
}
