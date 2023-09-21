package com.example.cmpcontacts.core.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap

@Composable
expect fun rememberBitmapFromByte(bytes: ByteArray?): ImageBitmap?