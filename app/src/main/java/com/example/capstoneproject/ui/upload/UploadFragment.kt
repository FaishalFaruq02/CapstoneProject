package com.example.capstoneproject.ui.upload

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsetsController
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.FragmentUploadBinding
import com.example.capstoneproject.ui.upload.myupload.MyUploadActivity

// fragment ini, pada saat landscape masih error (force closed)

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
        val root: View = binding.root

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireActivity().window.setDecorFitsSystemWindows(false)
            requireActivity().window.insetsController?.apply {
                hide(WindowInsetsController.BEHAVIOR_SHOW_BARS_BY_SWIPE)
            }
        } else {
            @Suppress("DEPRECATION")
            requireActivity().window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }

        // cardview untuk my upload
        val cardMyUpload = root.findViewById<CardView>(R.id.card_my_upload)
        cardMyUpload.setOnClickListener {
            // Tambahkan aksi navigasi ke halaman upload buku

            // Intent ke MyUploadActivity saat klik card My Upload
            val intent = Intent(requireContext(), MyUploadActivity::class.java)
            startActivity(intent)
        }

        // cardview untuk my book
        val cardMyBook = root.findViewById<CardView>(R.id.card_my_book)
        cardMyBook.setOnClickListener {
            // Tambahkan aksi navigasi ke halaman buku saya

        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}