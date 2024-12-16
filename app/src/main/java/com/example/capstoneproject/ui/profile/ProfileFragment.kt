package com.example.capstoneproject.ui.profile

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsetsController
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.capstoneproject.R
import com.example.capstoneproject.ui.profile.settings.SettingsActivity
import com.example.capstoneproject.ui.profile.status.MyStatusActivity

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Handle "My Status"
        val cardMyStatus: CardView = view.findViewById(R.id.card_my_status)
        cardMyStatus.setOnClickListener {
            val intent = Intent(requireContext(), MyStatusActivity::class.java)
            startActivity(intent)
        }

        // Handle "My Library"
        val cardMyLibrary: CardView = view.findViewById(R.id.card_my_library)
        cardMyLibrary.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_profile_to_home)
        }

        // Handle "My Upload"
        val cardMyUpload: CardView = view.findViewById(R.id.card_my_upload)
        cardMyUpload.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_profile_to_upload)
        }

        // Handle "Settings"
        val cardSetting: CardView = view.findViewById(R.id.card_setting)
        cardSetting.setOnClickListener {
            val intent = Intent(requireContext(), SettingsActivity::class.java)
            startActivity(intent)
        }

        return view
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
}