package com.example.capstoneproject.ui.home

import com.google.gson.annotations.SerializedName

data class BookResponse(

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("message")
	val message: String
)

data class DataItem(

	@field:SerializedName("profil_Name")
	val profilName: String,

	@field:SerializedName("author")
	val author: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("rating")
	val rating: Any,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("general_category")
	val generalCategory: String,

	@field:SerializedName("Title")
	val title: String,

	@field:SerializedName("review/score")
	val reviewScore: Double,

	@field:SerializedName("authors")
	val authors: String,

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("publisher")
	val publisher: String,

	@field:SerializedName("categories")
	val categories: String,

	@field:SerializedName("publishedDate")
	val publishedDate: Any
)
