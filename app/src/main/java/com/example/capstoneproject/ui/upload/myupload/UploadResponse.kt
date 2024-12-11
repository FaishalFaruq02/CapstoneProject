package com.example.capstoneproject.ui.upload.myupload

import com.google.gson.annotations.SerializedName

data class UploadResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("book_info")
	val bookInfo: BookInfo
)

data class BookInfo(

	@field:SerializedName("profil_Name")
	val profilName: String,

	@field:SerializedName("general_category")
	val generalCategory: String,

	@field:SerializedName("Title")
	val title: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("review/score")
	val reviewScore: Any,

	@field:SerializedName("authors")
	val authors: String
)
