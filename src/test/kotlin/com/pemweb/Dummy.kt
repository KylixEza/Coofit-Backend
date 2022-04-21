package com.pemweb

import com.oreyo.model.user.UserResponse
import com.pemweb.model.user.UserBody

object Dummy {
	val dummyUserBody = UserBody(
		"kylix dummy",
		"123456",
		"Way to home",
		"",
		1000,
		"kylix.dev@gmail.com",
		"08123456789",
		5000
	)
	
	val dummyUpdatedUserBody = UserBody(
		"kylix",
		"100902",
		"Jalan Veteran Dalam Gg V, No. 398",
		"https://kylixeza.png",
		1200,
		"kylix.dev@gmail.com",
		"088805594697",
		5000
	)
	
	val dummyUserResponse = UserResponse(
		"abcdef",
		"kylix",
		"100902",
		"Jalan",
		"",
		1000,
		"kylix.dev@gmail.com",
		"08123456789",
		5000
	)
	
	val existsUser = UserBody(
		"John",
		"123456789",
		"Malang Street",
		"",
		1000,
		"johndoe@gmail.com",
		"08123456789",
		5000
	)
	
	val dummyFavoriteMenuId = "MENU001"
	
	val dummiesFavoriteMenuId = listOf<String>()
}