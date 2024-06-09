package com.example.wisprmagic.data

interface KeyboardConfigRecordRepository {
    suspend fun updateKeyboardConfigRecord(keyboardConfigRecord: KeyboardConfigRecord)

    suspend fun getKeyboardConfigRecord(): KeyboardConfigRecord?
}