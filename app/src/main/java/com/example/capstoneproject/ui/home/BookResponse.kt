package com.example.capstoneproject.ui.home

import com.google.gson.annotations.SerializedName

data class BookResponse(

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("message")
	val message: String
)

data class DataItem(

	@field:SerializedName("author")
	val author: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("rating")
	val rating: Double,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("user")
	val user: String,

	@field:SerializedName("genre")
	val genre: String,

	@field:SerializedName("title")
	val title: String
)
