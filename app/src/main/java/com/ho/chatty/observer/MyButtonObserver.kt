package com.ho.chatty.observer

import android.text.Editable
import android.text.TextWatcher
import android.widget.ImageView
import com.ho.chatty.R

class MyButtonObserver(private val button: ImageView) : TextWatcher {
    override fun onTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {
        if (charSequence.toString().trim().isNotEmpty()) {
            button.isEnabled = true
            button.setImageResource(R.drawable.ic_send)
        } else {
            button.isEnabled = false
            button.setImageResource(R.drawable.ic_send_disabled)
        }
    }

    override fun beforeTextChanged(charSequence: CharSequence?, i: Int, i1: Int, i2: Int) {}
    override fun afterTextChanged(editable: Editable) {}
}
