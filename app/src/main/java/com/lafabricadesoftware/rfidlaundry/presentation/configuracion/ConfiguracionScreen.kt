package com.lafabricadesoftware.rfidlaundry.presentation.configuracion

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ConfiguracionScreen() {
    // Language selector and related code
    val activity = LocalContext.current as? Activity
    // Language change logic
    LaunchedEffect(Unit) {
        // Call the setApplicationLocales here
        activity?.recreate()
    }
    // Additional configuration screen code
}

@Preview
@Composable
fun ConfiguracionScreenPreview() {
    ConfiguracionScreen()
}