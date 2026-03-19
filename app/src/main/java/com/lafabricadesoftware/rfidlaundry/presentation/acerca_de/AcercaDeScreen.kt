package com.lafabricadesoftware.rfidlaundry.presentation.acerca_de

import androidx.compose.foundation.Image
import androidx.compose.ui.text.style.TextAlign
import com.lafabricadesoftware.rfidlaundry.R
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.lafabricadesoftware.rfidlaundry.presentation.acerca_de.components.TopBar
import kotlinx.coroutines.CoroutineScope

@Composable
fun AcercaDeScreen(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TopBar(scope, scaffoldState)
        Column(modifier = Modifier
            .fillMaxSize()
            .weight(1f)
        ) {
            Text(
                text = stringResource(R.string.about_description),
                color = Color.DarkGray,
                fontSize = 16.sp,
                textAlign = TextAlign.Justify,
            )
        }
        Image(
            painter = painterResource(R.drawable.lfslogo),
            contentDescription = stringResource(R.string.logo_description),
            modifier = Modifier
                .align(Alignment.End)
                .padding(16.dp)
        )
    }
}
