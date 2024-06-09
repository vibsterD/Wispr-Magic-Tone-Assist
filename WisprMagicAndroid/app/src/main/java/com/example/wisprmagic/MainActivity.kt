package com.example.wisprmagic

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.wisprmagic.ui.WisprKeyboardSettingsApp
import com.example.wisprmagic.ui.theme.WisprMagicTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WisprMagicTheme {
                // A surface container using the 'background' color from the theme
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFFF5F5F5),
                                    Color(0xFFF5F5F5),
                                    Color(0xFFF5F5F5),
                                    Color(0xFFD9C2C0),
                                    Color(0xFF9F88C8)

                                ),
                                startY = 0f,
                                endY = Float.POSITIVE_INFINITY
                            )
                        )
                    ,
//                    color = MaterialTheme.colorScheme.background
                ) {
//                    Greeting("Android")
                    WisprKeyboardSettingsApp(Modifier.fillMaxSize())
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WisprMagicTheme {
        // A surface container using the 'background' color from the theme
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFF5F5F5),
                            Color(0xFFF5F5F5),
                            Color(0xFFF5F5F5),
                            Color(0xFFD9C2C0),
                            Color(0xFF9F88C8)
                        ),
                        startY = 0f,
                        endY = Float.POSITIVE_INFINITY
                    )
                )
            ,
//                    color = MaterialTheme.colorScheme.background
        ) {
//            Greeting("Android")
            WisprKeyboardSettingsApp(Modifier.fillMaxSize())
        }
    }
}