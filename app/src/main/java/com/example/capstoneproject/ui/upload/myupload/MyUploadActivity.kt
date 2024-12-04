package com.example.capstoneproject.ui.upload.myupload

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.ActivityMyUploadBinding

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
            updateUploadButtonState()
        }

        binding.galleryButton.setOnClickListener { startGallery() }
        binding.cameraButton.setOnClickListener { checkCameraPermission() }
        binding.uploadButton.setOnClickListener {
            if (currentImageUri != null) {
                uploadImage()
            } else {
                Toast.makeText(this, "Choose an Image First!", Toast.LENGTH_SHORT).show()
            }
        }
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
            updateUploadButtonState()
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
                updateUploadButtonState()
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
        binding.uploadButton.isEnabled = currentImageUri != null
    }

    private fun uploadImage() {
        Toast.makeText(this, "Fitur ini belum tersedia", Toast.LENGTH_SHORT).show()
    }
}
