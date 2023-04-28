package com.example.rickandmortyapp

import android.app.Application
import android.content.Context
import com.example.rickandmortyapp.di.AppComponent
import com.example.rickandmortyapp.di.DaggerAppComponent

class MyApplication : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    private fun initDagger() {
        appComponent = DaggerAppComponent.builder().context(this).build()
    }
}

