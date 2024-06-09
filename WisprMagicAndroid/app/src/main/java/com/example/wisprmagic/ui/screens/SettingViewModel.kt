package com.example.wisprmagic.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wisprmagic.data.KeyboardConfigRecord
import com.example.wisprmagic.data.KeyboardConfigRecordRepository
import kotlinx.coroutines.launch


sealed interface SettingsUiState {
    data class Success(val currentSettings: KeyboardConfigRecord): SettingsUiState
    object Error: SettingsUiState
//    object Loading: SettingsUiState
    object None: SettingsUiState
    data class Modified(val currentSettings: KeyboardConfigRecord): SettingsUiState
}

class SettingViewModel(private val keyboardConfigRecordRepository: KeyboardConfigRecordRepository): ViewModel() {
    var settingsUiState: SettingsUiState by mutableStateOf(SettingsUiState.None)
        private set


    fun modifyKeyboardConfigRecord(newKeyboardConfigRecord: KeyboardConfigRecord) {
        settingsUiState = SettingsUiState.Modified(newKeyboardConfigRecord)
    }
    
    fun commitKeyboardConfigRecord() {
        if(settingsUiState !is SettingsUiState.Modified) return

        viewModelScope.launch {
            val newKeyboardConfigRecord = (settingsUiState as SettingsUiState.Modified).currentSettings
            keyboardConfigRecordRepository.updateKeyboardConfigRecord(newKeyboardConfigRecord)
            settingsUiState = SettingsUiState.Success(newKeyboardConfigRecord)
        }

    }

    fun loadCurrentKeyboardConfig() {
        viewModelScope.launch {
            keyboardConfigRecordRepository.getKeyboardConfigRecord()?.let {
                settingsUiState = SettingsUiState.Success(it)
            }
        }
    }

}