package com.example.wisprmagic.ui.screens

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wisprmagic.WisprKeyboardService
import com.example.wisprmagic.data.KeyboardConfigRecord
import com.example.wisprmagic.ui.AppViewModelProvider
import com.example.wisprmagic.ui.theme.Smoke
import com.example.wisprmagic.ui.theme.Twilight

data class SettingData(
    val featureName: String,
    val getEnabled: (KeyboardConfigRecord) -> Boolean,
    val modifyConfig: (KeyboardConfigRecord, Boolean) -> KeyboardConfigRecord
)

@Composable
fun SettingScreen() {
    val settingViewModel: SettingViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val settingsUiState = settingViewModel.settingsUiState
    val settingsData = listOf<SettingData>(
        SettingData("Wispr Magic", {it.enableWisprMagic}, {config, enabled -> config.copy(enableWisprMagic = enabled)}),
        SettingData("Auto Correct", {it.enableAutoCorrect}, {config, enabled -> config.copy(enableAutoCorrect = enabled)}),
        SettingData("Auto Capitalize", {it.enableAutoCapitalize}, {config, enabled -> config.copy(enableAutoCapitalize = enabled)}),
        SettingData("Swipe Type", {it.enableSwipeType}, {config, enabled -> config.copy(enableSwipeType = enabled)})
    )
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when(settingsUiState) {
            is SettingsUiState.Success -> {
                val currentSettings = (settingsUiState as SettingsUiState.Success).currentSettings
                DisplaySettings(settingsData = settingsData, currentSettings = currentSettings, settingViewModel = settingViewModel)
            }
            is SettingsUiState.Modified -> {
                val currentSettings = (settingsUiState as SettingsUiState.Modified).currentSettings
                DisplaySettings(settingsData = settingsData, currentSettings = currentSettings, settingViewModel = settingViewModel)
            }
            is SettingsUiState.Error -> {
                Text("Error")
            }
            is SettingsUiState.None -> {
                LaunchedEffect(Unit) {
                    settingViewModel.loadCurrentKeyboardConfig()
                }
            }

        }
        
        Row(
            modifier = Modifier.fillMaxWidth().padding(3.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { settingViewModel.loadCurrentKeyboardConfig() },
                enabled = settingsUiState is SettingsUiState.Modified,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Twilight,
                    contentColor = Smoke,
                    disabledContainerColor = Twilight.copy(0.5f),
                    disabledContentColor = Smoke.copy(0.5f),
                )
                ) {
                Text(text = "Cancel")
            }
            Button(
                onClick = {
                    settingViewModel.commitKeyboardConfigRecord()
                    val intent = Intent(context, WisprKeyboardService::class.java).apply {
                        action = WisprKeyboardService.ACTION_RELOAD_CONFIGURATION
                    }
                    context.startService(intent)
                          },
                enabled = settingsUiState is SettingsUiState.Modified,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Twilight,
                    contentColor = Smoke,
                    disabledContainerColor = Twilight.copy(0.5f),
                    disabledContentColor = Smoke.copy(0.5f),
                )
                ) {
                Text(text = "Save")
            }
        }
        
    }
    


}

@Composable
fun DisplaySettings(settingsData: List<SettingData>, currentSettings: KeyboardConfigRecord, settingViewModel: SettingViewModel) {
    LazyColumn() {
        items(settingsData.size) { index ->
            val settingData = settingsData[index]
            val enabled = settingData.getEnabled(currentSettings)
            SettingSwitchCard(settingData.featureName, enabled){
                settingViewModel.modifyKeyboardConfigRecord(settingData.modifyConfig(currentSettings, it))
            }
        }
    }
}



@Composable
fun SettingSwitchCard(featureName: String, enabled: Boolean, onCheckedChange: (Boolean) -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = featureName,
                fontSize = MaterialTheme.typography.bodyLarge.fontSize
            )
            Text(
                text = if (enabled) "Enabled" else "Disabled",
                fontSize = MaterialTheme.typography.bodySmall.fontSize,
                color = if (enabled) Twilight else Twilight.copy(alpha = 0.5f)
            )
        }
        Switch(
            checked = enabled,
            onCheckedChange = {
                onCheckedChange(it)
            },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Smoke,
                checkedTrackColor = Twilight
            )
        )
    }

}