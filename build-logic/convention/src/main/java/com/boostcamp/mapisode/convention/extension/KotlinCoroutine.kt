package com.boostcamp.mapisode.convention.extension

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureKotlinCoroutine(commonExtension: CommonExtension<*, *, *, *, *, *>) {
    commonExtension.apply {
        dependencies {
            implementation(libs.getBundle("coroutines"))
        }
    }
}
