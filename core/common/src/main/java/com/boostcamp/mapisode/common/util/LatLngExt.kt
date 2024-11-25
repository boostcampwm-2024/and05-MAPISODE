package com.boostcamp.mapisode.common.util

import com.boostcamp.mapisode.model.EpisodeLatLng
import com.naver.maps.geometry.LatLng

fun LatLng.toEpisodeLatLng() = EpisodeLatLng(latitude, longitude)
