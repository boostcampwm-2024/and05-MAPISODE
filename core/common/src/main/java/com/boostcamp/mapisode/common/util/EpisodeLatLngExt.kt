package com.boostcamp.mapisode.common.util

import com.boostcamp.mapisode.model.EpisodeLatLng
import com.naver.maps.geometry.LatLng

fun EpisodeLatLng.distanceTo(other: EpisodeLatLng) =
	calculateDistance(this.latitude, this.longitude, other.latitude, other.longitude)

fun EpisodeLatLng.toLatLng() = LatLng(this.latitude, this.longitude)
