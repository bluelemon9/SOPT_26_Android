package com.example.sopt_seminar1_hw2_hw4

import android.content.Context
import android.widget.Toast

fun Context.showToast(msg : String){
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}