package com.example.wisprmagic.data

import android.content.Context

interface AppContainer {
    val keyboardConfigRecordRepository: KeyboardConfigRecordRepository
}

class AppDataContainer(private val context: Context): AppContainer {
    override val keyboardConfigRecordRepository: KeyboardConfigRecordRepository by lazy {
        OfflineKeyboardConfigRecordRepository(KeyboardConfigRecordDatabase
            .getKeyboardConfigRecordDatabase(context).keyboardConfigRecordDao())
    }
}