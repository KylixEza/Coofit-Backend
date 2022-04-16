package com.pemweb.model.login

import com.google.gson.annotations.SerializedName

data class LoginResponse(
	@field:SerializedName("isExist")
	val isExist: Boolean,
	
	@field:SerializedName("uid")
	val uid: String?
)
