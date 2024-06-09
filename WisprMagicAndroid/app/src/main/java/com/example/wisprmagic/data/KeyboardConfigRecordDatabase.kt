package com.example.wisprmagic.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [KeyboardConfigRecord::class], version = 1, exportSchema = false)
abstract class KeyboardConfigRecordDatabase: RoomDatabase() {
    abstract fun keyboardConfigRecordDao(): KeyboardConfigRecordDAO

    private class KeyboardConfigRecordDatabaseCallback(
        private val scope: CoroutineScope
    ): RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            Instance?.let { database ->
                scope.launch {
                    database.keyboardConfigRecordDao().updateKeyboardConfigRecord(KeyboardConfigRecord())
                }
            }
        }
    }

    companion object {
        @Volatile
        private var Instance: KeyboardConfigRecordDatabase? = null

        fun getKeyboardConfigRecordDatabase(context: Context): KeyboardConfigRecordDatabase {
            return Instance?: synchronized(this) {
                Room.databaseBuilder(context, KeyboardConfigRecordDatabase::class.java, "KeyboardConfigRecordDatabase")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }

    }
}


