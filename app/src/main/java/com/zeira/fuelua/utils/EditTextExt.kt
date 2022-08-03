package com.zeira.fuelua.utils

import android.widget.EditText
import com.google.android.material.textfield.TextInputEditText
import com.zeira.fuelua.R

fun TextInputEditText.setFieldFocusListener() {
    setOnFocusChangeListener { v, hasFocus ->
        if (v.isFocused) {
            setBackgroundResource(R.drawable.custom_edit_text_selected)
        } else if (!v.isFocused) {
            setBackgroundResource(R.drawable.custom_edit_text_unselected)
        }
    }
}

fun EditText.textStr() = text.toString()