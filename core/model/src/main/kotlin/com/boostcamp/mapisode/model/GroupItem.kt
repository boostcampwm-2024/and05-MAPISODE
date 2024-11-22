package com.boostcamp.mapisode.model

data class GroupItem(
	val name: String,
	val imageUrl: String,
	val description: String,
	val createdAt: String,
	val adminUser: String,
	val users: List<User> = listOf(),	// 태희님이 이 클래스 쓰시고 계셔서 초기값 부여해서 사용중입니다. - 태웅
)
