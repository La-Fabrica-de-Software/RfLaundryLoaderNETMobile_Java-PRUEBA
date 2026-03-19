package com.lafabricadesoftware.rfidlaundry.presentation.busqueda_prendas.seleccion_subcliente.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.lafabricadesoftware.rfidlaundry.domain.model.MasterSubClientes
import com.lafabricadesoftware.rfidlaundry.presentation.navigation.Screens

@Composable
fun SubClienteItem(navController: NavHostController, item: MasterSubClientes) {
    Row (
        modifier = Modifier.fillMaxWidth().background(Color(0xFFFFFFFF))
            .clickable { navController.navigate(Screens.BusquedaPrendasPrenda.passSubCliente(Gson().toJson(item)))},
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.size(46.dp).padding(0.dp).background(Color(0xFF4CAF50)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(Icons.Filled.Person, "", tint = Color.White)
        }
        Column(modifier = Modifier.weight(1f).padding(start = 10.dp, top = 0.dp, end = 10.dp, bottom = 0.dp)
        ) {
            Text(modifier = Modifier,
                text = item.Nombre,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colors.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start
            )
        }
    }
}