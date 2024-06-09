package com.example.wisprmagic.ui

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wisprmagic.R
import com.example.wisprmagic.WisprKeyboardService
import com.example.wisprmagic.ui.screens.SettingScreen
import com.example.wisprmagic.ui.screens.SettingSwitchCard
import com.example.wisprmagic.ui.theme.Smoke
import com.example.wisprmagic.ui.theme.Twilight
import com.example.wisprmagic.ui.theme.WisprMagicTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WisprKeyboardSettingsApp(modifier: Modifier) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.wispr_stacked_dark),
                    contentDescription = "Wispr Magic Logo",
                    modifier = Modifier.size(50.dp)
                )
            }
        },
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.primary

    ){innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Wispr Keyboard Settings",
                style = MaterialTheme.typography.headlineSmall
            )

            SettingScreen()

        }

    }
}



@Preview
@Composable
fun WisprKeyboardSettingsAppPreview() {
    WisprMagicTheme {
//        WisprKeyboardSettingsApp(Modifier.fillMaxSize())
        Text(
            text = "Wispr Keyboard Settings",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.fillMaxSize()
        )
    }

}