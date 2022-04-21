package com.pemweb.model.user

import com.google.gson.annotations.SerializedName

data class UserBody(
	@field:SerializedName("username")
	val username: String,
	
	@field:SerializedName("password")
	val password: String,
	
	@field:SerializedName("address")
	val address: String,
	
	@field:SerializedName("avatar")
	val avatar: String,
	
	@field:SerializedName("coofit_wallet")
	val coofitWallet: Int,
	
	@field:SerializedName("email")
	val email: String,
	
	@field:SerializedName("phone_number")
	val phoneNumber: String,
	
	@field:SerializedName("xp")
	val xp: Int
)
