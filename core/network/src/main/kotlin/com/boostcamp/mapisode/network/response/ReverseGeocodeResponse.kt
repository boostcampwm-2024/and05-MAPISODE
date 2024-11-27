package com.boostcamp.mapisode.network.response

import kotlinx.serialization.Serializable

@Serializable
data class ReverseGeocodeResponse(val status: Status?, val results: List<AddressResult>?)

@Serializable
data class Status(val code: Int?, val name: String?, val message: String?)

@Serializable
data class AddressResult(val name: String?, val code: Code?, val region: Region?, val land: Land?)

@Serializable
data class Code(val id: String?, val type: String?, val mappingId: String?)

@Serializable
data class Region(
	val area0: Area?,
	val area1: Area?,
	val area2: Area?,
	val area3: Area?,
	val area4: Area?,
)

@Serializable
data class Area(val name: String?, val coords: Coords?)

@Serializable
data class Coords(val center: Center?)

@Serializable
data class Center(val crs: String?, val x: Double?, val y: Double?)

@Serializable
data class Land(
	val type: String?,
	val number1: String?,
	val number2: String?,
	val addition0: Addition?,
	val addition1: Addition?,
	val addition2: Addition?,
	val name: String?,
	val coords: Coords?,
)

@Serializable
data class Addition(val type: String?, val value: String?)
