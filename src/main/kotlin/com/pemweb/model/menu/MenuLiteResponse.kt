package com.pemweb.model.menu

import com.google.gson.annotations.SerializedName

data class MenuLiteResponse(
	@field:SerializedName("menu_id")
	val menuId: String,
	
	@field:SerializedName("difficulty")
	val difficulty: String,
	
	@field:SerializedName("calories")
	val calories: Int,
	
	@field:SerializedName("cook_time")
	val cookTime: Int,
	
	@field:SerializedName("image")
	val image: String,
	
	@field:SerializedName("rating")
	val rating: Double? = 0.0,
	
	@field:SerializedName("title")
	val title: String,
)
