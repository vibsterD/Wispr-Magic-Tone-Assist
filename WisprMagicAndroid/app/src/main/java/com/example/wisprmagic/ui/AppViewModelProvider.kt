package com.example.wisprmagic.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.wisprmagic.WisprKeyboardApplication
import com.example.wisprmagic.ui.screens.SettingViewModel


object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            SettingViewModel(wisprKeyboardApplication().container.keyboardConfigRecordRepository)
        }
    }
}


fun CreationExtras.wisprKeyboardApplication(): WisprKeyboardApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as WisprKeyboardApplication)