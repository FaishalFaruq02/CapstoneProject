package com.example.capstoneproject.ui.library

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.capstoneproject.data.api.ApiConfig
import com.example.capstoneproject.ui.home.BookResponse
import com.example.capstoneproject.ui.home.DataItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LibraryViewModel : ViewModel() {
    private val _libraryBooks = MutableLiveData<List<DataItem>>(emptyList())
    val libraryBooks: LiveData<List<DataItem>> get() = _libraryBooks

    private val _books = MutableLiveData<List<DataItem>>()
    val books: LiveData<List<DataItem>> get() = _books

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    fun fetchBooks() {
        _isLoading.postValue(true)
        val client = ApiConfig.getApiService().getBooks()
        client.enqueue(object : Callback<BookResponse> {
            override fun onResponse(call: Call<BookResponse>, response: Response<BookResponse>) {
                _isLoading.postValue(false)
                if (response.isSuccessful) {
                    _books.postValue(response.body()?.data ?: emptyList())
                } else {
                    _errorMessage.postValue("Failed to load books: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<BookResponse>, t: Throwable) {
                _isLoading.postValue(false)
                _errorMessage.postValue("Error: ${t.message}")
            }
        })
    }

    fun addBookToLibrary(book: DataItem) {
        val currentBooks = _libraryBooks.value?.toMutableList() ?: mutableListOf()
        currentBooks.add(book)
        _libraryBooks.value = currentBooks
    }
}
