package com.boostcamp.mapisode.common.util

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

object UuidGenerator {
	@OptIn(ExperimentalUuidApi::class)
	fun generate(): String =
		Uuid.random().toString().replace("-", "")
}
