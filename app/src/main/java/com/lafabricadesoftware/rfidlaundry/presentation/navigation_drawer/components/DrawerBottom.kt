package com.lafabricadesoftware.rfidlaundry.presentation.navigation_drawer.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lafabricadesoftware.rfidlaundry.R

@Composable
fun DrawerBottom() {

    Column(modifier = Modifier
        .fillMaxWidth()
        .background(color = Color.White)
    ) {
        Text(
            text = stringResource(R.string.company_name),
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(12.dp)
                .align(Alignment.CenterHorizontally)
        )
    }


}
