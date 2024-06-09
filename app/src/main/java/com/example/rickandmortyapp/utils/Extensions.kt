package com.example.rickandmortyapp.utils

import android.content.Context
import android.widget.Toast
import com.example.rickandmortyapp.MyApplication
import com.example.rickandmortyapp.di.AppComponent

fun Context.toast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is MyApplication -> appComponent
        else -> this.applicationContext.appComponent
    }