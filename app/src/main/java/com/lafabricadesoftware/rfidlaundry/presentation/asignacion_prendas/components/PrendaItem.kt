package com.lafabricadesoftware.rfidlaundry.presentation.asignacion_prendas.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lafabricadesoftware.rfidlaundry.domain.model.ui.Prenda
import com.lafabricadesoftware.rfidlaundry.domain.model.ui.PrendaCliente
import com.lafabricadesoftware.rfidlaundry.domain.model.ui.PrendaSubCliente

@Composable
fun GrupoPrendaDesconocidaItem(prendas: List<Prenda>) {

    Column(modifier = Modifier.padding(4.dp, 0.dp, 4.dp, 0.dp).background(Color.Transparent)) {
        Row(modifier = Modifier
            .fillMaxWidth()
        ) {
            Text(modifier = Modifier.weight(1f).padding(0.dp, 5.dp).background(Color.Transparent),
                text = "Sin identificar",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFFFF7043)
            )

            val total = prendas.count()
            Text(modifier = Modifier.padding(0.dp, 5.dp).background(Color.Transparent),
                text = "Total: $total",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFFFF7043)
            )
        }

        Column(modifier = Modifier.background(Color.Transparent)
        ) {
            prendas.forEach() { prendaUnknown ->
                PrendaItem(prendaUnknown)
                Spacer(modifier = Modifier.height(2.dp))
            }
        }
    }
}

@Composable
fun PrendaClienteItem(item: PrendaCliente) {

//    Card(modifier = Modifier
//        .fillMaxWidth()
//        .padding(5.dp)
//        .background(Color.Transparent),
//        elevation = 5.dp,
//        backgroundColor = Color(0xEFEFEFEF)
//    ) {
//
//    }

    Column(modifier = Modifier.padding(4.dp, 0.dp).background(Color.Transparent)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(modifier = Modifier.weight(1f).padding(0.dp, 5.dp).background(Color.Transparent),
                text = if (item.nombreCliente == "") {"Sin identificar"} else {item.nombreCliente},
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (item.nombreCliente == "") { Color(0xFFFF7043) } else { Color.Black }
            )

            var total = 0
            item.listadoPrendaSubCliente.forEach() { total += it.listadoPrenda.count() }

            Text(modifier = Modifier.padding(0.dp, 5.dp).background(Color.Transparent),
                text = "Total: $total",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (item.nombreCliente == "") { Color(0xFFFF7043) } else { Color.Black }
            )
        }

        Column(modifier = Modifier.background(Color.Transparent)) {
            item.listadoPrendaSubCliente.forEach() { prendaSubCliente -> PrendaSubClienteItem(prendaSubCliente) }
        }
    }
}

@Composable
fun PrendaSubClienteItem(item: PrendaSubCliente) {
    if (item.nombreSubCliente != "") {
        Text(modifier = Modifier.padding(0.dp, 3.dp),
            text = item.nombreSubCliente + if (item.isBorrado) { " (Borrado)" } else {""},
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Gray
        )
    }

    Column(modifier = Modifier.background(Color.Transparent).padding(0.dp), verticalArrangement = Arrangement.Top) {
        item.listadoPrenda.forEach { prenda ->
            PrendaItem(prenda)
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
fun PrendaItem( item: Prenda /*, onSelected: () -> Unit */) {
    Row(
        modifier = Modifier.fillMaxWidth().background(Color(0xFFFFFFFF)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.size(46.dp).padding(0.dp)
            .background(
                if (item.isExiste) {
                    if (item.isBorrado || item.isClienteBorrado || item.isSubClienteBorrado) {
                        Color(0xFF8B8B8B)
                    } else {
                        Color(0xFF4CAF50)
                    }
                } else {
                    Color(0xFFFF7043)
                }
            ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (item.isExiste) {
                if (item.isBorrado) {
                    Icon(Icons.Filled.Close, "", tint = Color.White)
                } else {
                    Icon(Icons.Filled.Check, "", tint = Color.White)
                }
            } else {
                Icon(Icons.Filled.QuestionMark, "", tint = Color.White)
            }
        }

        Column(modifier = Modifier.weight(1f).padding(start = 10.dp, top = 0.dp, end = 10.dp, bottom = 0.dp)) {
            Text(modifier = Modifier,
                text = item.tag + if (item.isBorrado) { " (Borrado)" } else { "" },
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
                            Prenda(0, "", "QWERTY123456QWERTY123456", "M", false, false, true),
                            Prenda(0, "", "ASDFGH567890QWERTY123456", "M", false, false, true),
                            Prenda(0, "", "ASDFGH567890QWERTY123456", "M", false, false, true)
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
                            Prenda(0, "", "QWERTY123456QWERTY123456", "M", true, false, true),
                            Prenda(0, "", "ASDFGH567890QWERTY123456", "M", true, true, true)
                        )
                    ),
                    PrendaSubCliente(
                        0,
                        "Subcliente 1.2",
                        mutableListOf(
                            Prenda(0, "", "QWERTY123456QWERTY123456", "M", true, false, true)
                        )
                    ),
                    PrendaSubCliente(
                        0,
                        "Subcliente 1.3",
                        mutableListOf(
                            Prenda(0, "", "QWERTY123456QWERTY123456", "M", true, false, true)
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
                            Prenda(0, "", "QWERTY123456QWERTY123456", "M", true, true, true)
                        )
                    ),
                    PrendaSubCliente(
                        0,
                        "Subcliente 2.2",
                        mutableListOf(
                            Prenda(0, "", "QWERTY123456QWERTY123456", "M", true, false, true),
                            Prenda(0, "", "ASDFGH567890QWERTY123456", "M", true, true, true)
                        )
                    )
                )
            )
        )
    }
}