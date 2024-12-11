package com.example.capstoneproject.data.api

import com.example.capstoneproject.ui.home.BookResponse
import com.example.capstoneproject.ui.upload.myupload.UploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @GET("get_buku")
    fun getBooks(): Call<BookResponse>

    @Multipart
    @POST("upload")
    suspend fun uploadBook(
        @Part("title") title: RequestBody,
        @Part("authors") authors: RequestBody,
        @Part("general_category") category: RequestBody,
        @Part("rating") rating: RequestBody,
        @Part image: MultipartBody.Part
    ): Response<UploadResponse>
}