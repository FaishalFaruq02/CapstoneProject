package com.example.capstoneproject.ui.upload.myupload

import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.capstoneproject.R
import com.google.android.material.textfield.TextInputEditText

class MyUploadBookActivity : AppCompatActivity() {
    private lateinit var titleEditText: TextInputEditText
    private lateinit var descriptionEditText: TextInputEditText
    private lateinit var authorsEditText: TextInputEditText
    private lateinit var publisherEditText: TextInputEditText
    private lateinit var publishDateEditText: TextInputEditText
    private lateinit var generalCategoryEditText: TextInputEditText
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_my_upload_book)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val imageUri = intent.getStringExtra("IMAGE_URI")?.let { Uri.parse(it) }

        val imagePreview: ImageView = findViewById(R.id.imagePreview)

        if (imageUri != null) {
            imagePreview.setImageURI(imageUri)
        }
        titleEditText = findViewById(R.id.titleEditText)
        descriptionEditText = findViewById(R.id.descriptionEditText)
        authorsEditText = findViewById(R.id.authorsEditText)
        publisherEditText = findViewById(R.id.publisherEditText)
        publishDateEditText = findViewById(R.id.publishDateEditText)
        generalCategoryEditText = findViewById(R.id.generalCategoryEditText)
        saveButton = findViewById(R.id.saveButton)

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateInputs()
            }
            override fun afterTextChanged(s: Editable?) {}
        }

        titleEditText.addTextChangedListener(textWatcher)
        descriptionEditText.addTextChangedListener(textWatcher)
        authorsEditText.addTextChangedListener(textWatcher)
        publisherEditText.addTextChangedListener(textWatcher)
        publishDateEditText.addTextChangedListener(textWatcher)
        generalCategoryEditText.addTextChangedListener(textWatcher)

        validateInputs()
    }

    private fun validateInputs() {
        val isTitleValid = !titleEditText.text.isNullOrEmpty()
        val isDescriptionValid = !descriptionEditText.text.isNullOrEmpty()
        val isAuthorsValid = !authorsEditText.text.isNullOrEmpty()
        val isPublisherValid = !publisherEditText.text.isNullOrEmpty()
        val isPublishDateValid = !publishDateEditText.text.isNullOrEmpty()
        val isGeneralCategoryValid = !generalCategoryEditText.text.isNullOrEmpty()

        saveButton.isEnabled =
            isTitleValid && isDescriptionValid && isAuthorsValid && isPublisherValid && isPublishDateValid && isGeneralCategoryValid
    }
}
