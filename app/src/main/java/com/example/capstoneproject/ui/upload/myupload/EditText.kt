package com.example.capstoneproject.ui.upload.myupload

import android.content.Context
import android.graphics.Canvas
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import com.example.capstoneproject.R

class EditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs) {

    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Tidak ada aksi yang diperlukan sebelum teks berubah
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                validateInput()
            }

            override fun afterTextChanged(s: Editable) {
                // Tidak ada aksi yang diperlukan setelah teks berubah
            }
        })
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        hint = "Enter a Name"
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    private fun validateInput() {
        if (text.isNullOrEmpty()) {
            error = "This field cannot be empty"
        }
    }

}
