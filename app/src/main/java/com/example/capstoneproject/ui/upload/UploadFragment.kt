package com.example.capstoneproject.ui.upload

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsetsController
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.capstoneproject.databinding.FragmentUploadBinding
import com.example.capstoneproject.ui.upload.myupload.MyUploadActivity

class UploadFragment : Fragment() {

    private var _binding: FragmentUploadBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val uploadViewModel =
            ViewModelProvider(this).get(UploadViewModel::class.java)

        _binding = FragmentUploadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup tombol CardView ke upload
        binding.cardMyUpload.setOnClickListener {
            val intent = Intent(requireContext(), MyUploadActivity::class.java)
            startActivity(intent)
        }

        // Setup tombol CardView ke buku saya
        binding.cardMyBook.setOnClickListener {
            // Tambahkan aksi navigasi ke halaman buku saya
        }
    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireActivity().window.setDecorFitsSystemWindows(false)
            requireActivity().window.insetsController?.let { controller ->
                controller.hide(WindowInsetsController.BEHAVIOR_SHOW_BARS_BY_SWIPE)
            }
        } else {
            @Suppress("DEPRECATION")
            requireActivity().window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}