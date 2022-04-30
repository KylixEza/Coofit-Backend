package com.pemweb.model.menu

import com.google.gson.annotations.SerializedName
import com.oreyo.model.review.ReviewResponse

data class MenuResponse(
	
	@field:SerializedName("menu_id")
	val menuId: String,
	
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
	
	@field:SerializedName("ingredients")
	val ingredients: List<String>,
	
	@field:SerializedName("rating")
	val rating: Double?,
	
	@field:SerializedName("reviews")
	val reviews: List<ReviewResponse>,
	
	@field:SerializedName("steps")
	val steps: List<String>,
	
	@field:SerializedName("title")
	val title: String,
)
