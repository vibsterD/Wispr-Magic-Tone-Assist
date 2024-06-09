package com.example.wisprmagic.data

class OfflineKeyboardConfigRecordRepository(private val keyboardConfigRecordDAO: KeyboardConfigRecordDAO): KeyboardConfigRecordRepository {
    override suspend fun updateKeyboardConfigRecord(keyboardConfigRecord: KeyboardConfigRecord) {
        keyboardConfigRecordDAO.updateKeyboardConfigRecord(keyboardConfigRecord)
    }

    override suspend fun getKeyboardConfigRecord(): KeyboardConfigRecord? {
        if (keyboardConfigRecordDAO.isEmpty()) {
            keyboardConfigRecordDAO.updateKeyboardConfigRecord(KeyboardConfigRecord())
        }
        return keyboardConfigRecordDAO.getKeyboardConfigRecord()
    }
}