package com.pemweb.model.prediction

import com.google.gson.annotations.SerializedName

data class PredictionBody(
	@field:SerializedName("food")
	val food: String
)
