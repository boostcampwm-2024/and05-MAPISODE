package com.boostcamp.mapisode.convention.extension

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureComposeAndroid(commonExtension: CommonExtension<*, *, *, *, *, *>) {
	pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

	commonExtension.apply {
		buildFeatures {
			compose = true
		}

		dependencies {
			val composeBom = libs.getLibrary("compose-bom")
			implementation(platform(composeBom))
			implementation(libs.getBundle("compose"))
			debugImplementation(libs.getBundle("compose-debug"))
			// TODO: Test 의존성 추가
		}
	}
}
