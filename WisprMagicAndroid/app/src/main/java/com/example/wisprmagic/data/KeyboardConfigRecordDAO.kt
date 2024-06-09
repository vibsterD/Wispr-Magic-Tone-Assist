package com.example.wisprmagic.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface KeyboardConfigRecordDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateKeyboardConfigRecord(keyboardConfigRecord: KeyboardConfigRecord)

    @Query("SELECT * FROM KeyboardConfig LIMIT 1")
    suspend fun getKeyboardConfigRecord(): KeyboardConfigRecord?

    suspend fun isEmpty(): Boolean {
        return getKeyboardConfigRecord() == null
    }

}