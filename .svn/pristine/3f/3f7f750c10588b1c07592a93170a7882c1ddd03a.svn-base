package com.lafabricadesoftware.rfidlaundry.presentation.busqueda_prendas.components

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
import com.lafabricadesoftware.rfidlaundry.domain.model.MasterClientes
import com.lafabricadesoftware.rfidlaundry.presentation.navigation.Screens

@Composable
fun ClienteItem(navController: NavHostController, item: MasterClientes) {
    Row (
        modifier = Modifier.fillMaxWidth().background(Color(0xFFFFFFFF))
            .clickable { navController.navigate(Screens.BusquedaPrendasSubCliente.passCliente(Gson().toJson(item)))},
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

//@Composable
//@Preview(showBackground = true, backgroundColor = 0xEFEFEFEF)
//fun ClienteItemPreview() {
//    Column(
//    ) {
//        ClienteItem(navController = navController, item = MasterClientes(0, "Pepe", "", "", true))
//        Spacer(modifier = Modifier.height(2.dp))
//        ClienteItem(navController = navController, item = MasterClientes(0, "Jose", "", "", true))
//        Spacer(modifier = Modifier.height(2.dp))
//    }
//}

//fun PrendaItem( item: Prenda /*, onSelected: () -> Unit */) {
//    Row(
//        modifier = Modifier.fillMaxWidth().background(Color(0xFFFFFFFF)),
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        Column(modifier = Modifier.size(46.dp).padding(0.dp)
//            .background(
//                if (item.isExiste) {
//                    if (item.isBorrado) {
//                        Color(0xFF8B8B8B)
//                    } else {
//                        Color(0xFF4CAF50)
//                    }
//                } else {
//                    Color(0xFFFF7043)
//                }
//            ),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            if (item.isExiste) {
//                Icon(Icons.Filled.Check, "", tint = Color.White)
//            } else {
//                Icon(Icons.Filled.QuestionMark, "", tint = Color.White)
//            }
//        }
//
//        Column(modifier = Modifier
//            .weight(1f)
//            .padding(start = 10.dp, top = 0.dp, end = 10.dp, bottom = 0.dp)
//        ) {
//            Text(modifier = Modifier,
//                text = item.tag,
//                fontSize = 16.sp,
//                fontWeight = FontWeight.Normal,
//                color = MaterialTheme.colors.onSurface,
//                maxLines = 1,
//                overflow = TextOverflow.Ellipsis,
//                textAlign = TextAlign.Start
//            )
//        }
//    }
//}



/*
@Composable
@Preview(showBackground = true, backgroundColor = 0xEFEFEFEF)
fun FullList() {
    Column() {
        PrendaClienteItem(
            item = PrendaCliente(
                -1,
                "",
                mutableListOf(
                    PrendaSubCliente(
                        -1,
                        "",
                        mutableListOf(
                            Prenda(0, "", "QWERTY123456QWERTY123456", false, false, true),
                            Prenda(0, "", "ASDFGH567890QWERTY123456", false, false, true),
                            Prenda(0, "", "ASDFGH567890QWERTY123456", false, false, true)
                        )
                    )
                )
            )
        )
        PrendaClienteItem(
            item = PrendaCliente(
                0,
                "Cliente 1",
                mutableListOf(
                    PrendaSubCliente(
                        0,
                        "Subcliente 1.1",
                        mutableListOf(
                            Prenda(0, "", "QWERTY123456QWERTY123456", true, false, true),
                            Prenda(0, "", "ASDFGH567890QWERTY123456", true, true, true)
                        )
                    ),
                    PrendaSubCliente(
                        0,
                        "Subcliente 1.2",
                        mutableListOf(
                            Prenda(0, "", "QWERTY123456QWERTY123456", true, false, true)
                        )
                    ),
                    PrendaSubCliente(
                        0,
                        "Subcliente 1.3",
                        mutableListOf(
                            Prenda(0, "", "QWERTY123456QWERTY123456", true, false, true)
                        )
                    )
                )
            )
        )
        PrendaClienteItem(
            item = PrendaCliente(
                0,
                "Cliente 2",
                mutableListOf(
                    PrendaSubCliente(
                        0,
                        "Subcliente 2.1",
                        mutableListOf(
                            Prenda(0, "", "QWERTY123456QWERTY123456", true, true, true)
                        )
                    ),
                    PrendaSubCliente(
                        0,
                        "Subcliente 2.2",
                        mutableListOf(
                            Prenda(0, "", "QWERTY123456QWERTY123456", true, false, true),
                            Prenda(0, "", "ASDFGH567890QWERTY123456", true, true, true)
                        )
                    )
                )
            )
        )
    }
}
*/