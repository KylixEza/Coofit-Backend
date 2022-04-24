package com.pemweb.model.menu

import com.google.gson.annotations.SerializedName

data class MenuBody(
	
	@field:SerializedName("description")
	val description: String,
	
	@field:SerializedName("difficulty")
	val difficulty: String,
	
	@field:SerializedName("calories")
	val calories: Int,
	
	@field:SerializedName("cook_time")
	val cookTime: Int,
	
	@field:SerializedName("image")
	val image: String,
	
	@field:SerializedName("title")
	val title: String,
	
	@field:SerializedName("video_url")
	val videoUrl: String
)
