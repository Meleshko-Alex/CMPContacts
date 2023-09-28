package com.example.cmpcontacts.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.cmpcontacts.App
import com.example.cmpcontacts.core.presentation.ImagePickerFactory
import com.example.cmpcontacts.di.AppModule

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App(
                darkTheme = isSystemInDarkTheme(),
                dynamicColor = false,
                appModule = AppModule(LocalContext.current),
                imagePicker = ImagePickerFactory().createPicker()
            )
        }
    }
}

