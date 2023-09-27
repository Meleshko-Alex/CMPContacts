package com.example.cmpcontacts

import androidx.compose.ui.window.ComposeUIViewController
import com.example.cmpcontacts.di.AppModule
import platform.UIKit.UIScreen
import platform.UIKit.UIUserInterfaceStyle

/**
 * This function is used as a bridge between iOS UI and Compose
 * **/
fun MainViewController() = ComposeUIViewController {
    val isDarkTheme =
        UIScreen.mainScreen.traitCollection.userInterfaceStyle == UIUserInterfaceStyle.UIUserInterfaceStyleDark
    App(
        darkTheme = isDarkTheme,
        dynamicColor = false,
        appModule = AppModule()
    )
}