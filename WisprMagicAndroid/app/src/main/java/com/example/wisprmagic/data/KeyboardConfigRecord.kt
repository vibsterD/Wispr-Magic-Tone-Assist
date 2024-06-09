package com.example.wisprmagic.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "KeyboardConfig")
data class KeyboardConfigRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val enableAutoCorrect: Boolean = true,
    val enableSwipeType: Boolean = true,
    val enableWisprMagic: Boolean = true,
)
