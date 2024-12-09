package com.example.capstoneproject.data.api

import com.example.capstoneproject.ui.home.BookResponse
import retrofit2.http.GET
import retrofit2.Call

interface ApiService {
    @GET("get_buku")
    fun getBooks(): Call<BookResponse>
}