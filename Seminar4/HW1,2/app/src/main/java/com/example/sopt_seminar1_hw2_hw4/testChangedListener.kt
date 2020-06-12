package com.example.sopt_seminar1_hw2_hw4

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.textChangedListener(textChanged : (CharSequence?) -> Unit){
    this.addTextChangedListener(object : TextWatcher{
        override fun afterTextChanged(s: Editable?) = Unit

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // s에 대한 정보를 넘겨줘야 함
            textChanged(s)
        }
    })
}