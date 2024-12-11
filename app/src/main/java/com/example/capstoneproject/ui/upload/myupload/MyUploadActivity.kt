package com.example.capstoneproject.ui.upload.myupload

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.capstoneproject.R
import com.example.capstoneproject.data.api.ApiConfig
import com.example.capstoneproject.databinding.ActivityMyUploadBinding
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class MyUploadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyUploadBinding
    private var currentImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        currentImageUri = savedInstanceState?.getString("CURRENT_IMAGE_URI")?.let { Uri.parse(it) }
        if (currentImageUri != null) {
            showImage()
        }

        binding.titleEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                updateUploadButtonState()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.authorsEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                updateUploadButtonState()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.ratingEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                updateUploadButtonState()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.generalCategoryEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                updateUploadButtonState()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.galleryButton.setOnClickListener { startGallery() }
        binding.cameraButton.setOnClickListener { checkCameraPermission() }
        binding.saveButton.setOnClickListener { uploadBookData()}

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("CURRENT_IMAGE_URI", currentImageUri?.toString())
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Toast.makeText(this, "No media selected or action canceled.", Toast.LENGTH_SHORT).show()
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun startCameraIntent() {
        val imageUri = getImageUri(this)
        if (imageUri != null) {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            }
            try {
                cameraLauncher.launch(intent)
                currentImageUri = imageUri
            } catch (e: Exception) {
                Toast.makeText(this, "Failed to launch camera: ${e.message}", Toast.LENGTH_SHORT).show()
                Log.e("CameraIntent", "Error launching camera", e)
            }
        } else {
            Toast.makeText(this, "Unable to generate image URI", Toast.LENGTH_SHORT).show()
        }
    }

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                Toast.makeText(this, "Photo saved successfully!", Toast.LENGTH_SHORT).show()
                showImage()
            } else {
                Toast.makeText(this, "Camera action canceled.", Toast.LENGTH_SHORT).show()
            }
        }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                startCameraIntent()
            } else {
                Toast.makeText(this, "Camera permission is required", Toast.LENGTH_SHORT).show()
            }
        }

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            startCameraIntent()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    private fun showImage() {
        currentImageUri?.let { uri ->
            try {
                binding.previewImageView.setImageURI(uri)
            } catch (e: Exception) {
                Toast.makeText(this, "Failed to load image: ${e.message}", Toast.LENGTH_SHORT).show()
                Log.e("Image URI", "Error loading image", e)
            }
        } ?: run {
            Toast.makeText(this, "No image to display", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUploadButtonState() {
        val titleFilled = binding.titleEditText.text.toString().trim().isNotEmpty()
        val authorsFilled = binding.authorsEditText.text.toString().trim().isNotEmpty()
        val ratingFilled = binding.ratingEditText.text.toString().trim().isNotEmpty()
        val categoryFilled = binding.generalCategoryEditText.text.toString().trim().isNotEmpty()
        val imageSelected = currentImageUri != null

        binding.saveButton.isEnabled = titleFilled && authorsFilled && ratingFilled && categoryFilled && imageSelected
    }


    private fun uploadBookData() {
        val title = binding.titleEditText.text.toString().trim()
        val authors = binding.authorsEditText.text.toString().trim()
        val rating = binding.ratingEditText.text.toString().trim()
        val category = binding.generalCategoryEditText.text.toString().trim()

        if (title.isEmpty() || authors.isEmpty() || rating.isEmpty() || category.isEmpty() || currentImageUri == null) {
            Toast.makeText(this, "All fields and image must be filled", Toast.LENGTH_SHORT).show()
            return
        }

        val titleBody = title.toRequestBody("text/plain".toMediaTypeOrNull())
        val authorsBody = authors.toRequestBody("text/plain".toMediaTypeOrNull())
        val ratingBody = rating.toRequestBody("text/plain".toMediaTypeOrNull())
        val categoryBody = category.toRequestBody("text/plain".toMediaTypeOrNull())
        val file = File(getRealPathFromURI(currentImageUri!!))

        if (!file.exists()) {
            Toast.makeText(this, "File does not exist", Toast.LENGTH_SHORT).show()
            return
        }

        val imageBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imagePart = MultipartBody.Part.createFormData("image", file.name, imageBody)

        val apiService = ApiConfig.getApiService()
        lifecycleScope.launch {
            val response = apiService.uploadBook(
                titleBody, authorsBody, ratingBody, categoryBody, imagePart
            )
            if (response.isSuccessful) {
                Toast.makeText(this@MyUploadActivity, "Book uploaded successfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this@MyUploadActivity, "Failed to upload book", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun getRealPathFromURI(contentUri: Uri): String? {
        var filePath: String? = null
        val cursor = contentResolver.query(contentUri, null, null, null, null)
        cursor?.let {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndex("_data")
                filePath = it.getString(columnIndex)
            }
            it.close()
        }
        return filePath
    }

}
