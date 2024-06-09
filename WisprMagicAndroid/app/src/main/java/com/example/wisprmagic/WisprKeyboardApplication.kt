package com.example.wisprmagic

import android.app.Application
import com.example.wisprmagic.data.AppContainer
import com.example.wisprmagic.data.AppDataContainer

class WisprKeyboardApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}