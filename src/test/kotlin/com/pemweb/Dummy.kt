package com.pemweb

import com.oreyo.model.user.UserResponse
import com.pemweb.model.user.UserBody

object Dummy {
	val dummyUserBody = UserBody(
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
		"",
		"John",
		"123456789",
		"Malang Street",
		"",
		1000,
		"johndoe@gmail.com",
		"08123456789",
		5000
	)
}