package com.boostcamp.mapisode.convention

import com.boostcamp.mapisode.convention.extension.getBundle
import com.boostcamp.mapisode.convention.extension.getLibrary
import com.boostcamp.mapisode.convention.extension.implementation
import com.boostcamp.mapisode.convention.extension.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class MapisodeDataPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("mapisode.android.library")
                apply("mapisode.android.hilt")
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            dependencies {
                val retrofitBom = libs.getLibrary("retrofit-bom")
                implementation(platform(retrofitBom))
                implementation(project(":core:model"))
                implementation(project(":core:network"))
                implementation(libs.getLibrary("kotlinx-serialization"))
                implementation(libs.getBundle("retrofit"))
            }
        }
    }
}
