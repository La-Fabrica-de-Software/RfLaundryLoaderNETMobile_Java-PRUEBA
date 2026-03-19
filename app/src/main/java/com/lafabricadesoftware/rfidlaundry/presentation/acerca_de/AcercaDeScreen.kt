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
                text = "La Fábrica de Software es una empresa de ingeniería especializada en el desarrollo de sistemas informáticos integrados con nuestros equipos y máquinas, para la gestión automatizada y control textil en hospitales, fábricas, lavanderías industriales, tintorerías, etc.\n" +
                        "\n" +
                        "Contamos con personal cualificado dedicado a la investigación y desarrollo de soluciones innovadoras orientadas a atender las necesidades específicas de cada uno de nuestros clientes.\n" +
                        "\n" +
                        "Trabajamos cada día para obtener todas las ventajas que ofrece la tecnología RFID para la identificación y gestión de artículos en grandes volúmenes, proporcionado una mayor eficacia y eficiencia en los procesos de hospitales, centros socio-sanitarios, lavanderías y otras colectividades: industria, hoteles, gimnasios, etc.",
                color = Color.DarkGray,
                fontSize = 16.sp,
                textAlign = TextAlign.Justify,
            )
        }
        Image(
            painter = painterResource(R.drawable.lfslogo),
            contentDescription = "Logo",
            modifier = Modifier
                .align(Alignment.End)
                .padding(16.dp)
        )
    }
}