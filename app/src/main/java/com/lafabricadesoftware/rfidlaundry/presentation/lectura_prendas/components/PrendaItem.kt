package com.lafabricadesoftware.rfidlaundry.presentation.lectura_prendas.components

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
import kotlin.collections.count as count

@Composable
fun GrupoPrendaDesconocidaItem(prendas: List<Prenda>, group: Boolean = false) {
    if(prendas.count{item-> !item.isBorrado} > 0){
        Column(modifier = Modifier
            .padding(4.dp, 0.dp, 4.dp, 0.dp)
            .background(Color.Transparent)) {
            Row(modifier = Modifier
                .fillMaxWidth()
            ) {
                Text(modifier = Modifier
                    .weight(1f)
                    .padding(0.dp, 5.dp)
                    .background(Color.Transparent),
                    text = "Sin identificar",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFFFF7043)
                )

                val total = prendas.count()
                Text(modifier = Modifier
                    .padding(0.dp, 5.dp)
                    .background(Color.Transparent),
                    text = "Total: $total",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFFFF7043)
                )
            }
        }

        Column(modifier = Modifier.background(Color.Transparent)
        ) {
            //prendas.forEach() { prendaUnknown ->
            //    PrendaItem(prendaUnknown)
            //    Spacer(modifier = Modifier.height(2.dp))
            //}
            ListPrendra(items = prendas.filter { prenda -> !prenda.isBorrado }.toList(), group)
        }
    }
}

@Composable
fun ListPrendra(items:List<Prenda>, group: Boolean = false){
    if (!group){
       items.forEach { prenda ->
                PrendaItem(prenda)
                Spacer(modifier = Modifier.height(4.dp))
        }
    }
    else{
        var list=items.sortedWith(compareBy({it.nombrePrenda},{it.nombreModeloPrenda}, {it.talla}))
        var name:String=""
        var model:String=""
        var size:String=""
        list.forEach{ prenda ->
            //if(name!=prenda.nombrePrenda || model!=prenda.nombreModeloPrenda || size!=prenda.talla){
            if(model!=prenda.nombreModeloPrenda || size!=prenda.talla){
                name=prenda.nombrePrenda
                model=prenda.nombreModeloPrenda
                size=prenda.talla
                var total=items.count { it.nombrePrenda==name && it.nombreModeloPrenda==model && it.talla==size }
                PrendaItem(prenda, total)
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}
@Composable
fun PrendaClienteItem(item: PrendaCliente, group: Boolean = false) {

//    Card(modifier = Modifier
//        .fillMaxWidth()
//        .padding(5.dp)
//        .background(Color.Transparent),
//        elevation = 5.dp,
//        backgroundColor = Color(0xEFEFEFEF)
//    ) {
//
//    }

    Column(modifier = Modifier
        .padding(4.dp, 0.dp)
        .background(Color.Transparent)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(modifier = Modifier
                .weight(1f)
                .padding(0.dp, 5.dp)
                .background(Color.Transparent),
                text = if (item.nombreCliente == "") {"Sin identificar"} else {item.nombreCliente},
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (item.nombreCliente == "") { Color(0xFFFF7043) } else { Color.Black }
            )

            var total = 0
            item.listadoPrendaSubCliente
                .forEach() { total += it.listadoPrenda.count { item -> !item.isBorrado } }

            Text(modifier = Modifier
                .padding(0.dp, 5.dp)
                .background(Color.Transparent),
                text = "Total: $total",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (item.nombreCliente == "") { Color(0xFFFF7043) } else { Color.Black }
            )
        }

        Column(modifier = Modifier.background(Color.Transparent)) {
            item.listadoPrendaSubCliente.forEach() { prendaSubCliente -> PrendaSubClienteItem(prendaSubCliente, group) }
        }
    }
}

@Composable
fun PrendaSubClienteItem(item: PrendaSubCliente, group:Boolean = false) {
    if(item.listadoPrenda.count { prenda -> !prenda.isBorrado } > 0){
        if (item.nombreSubCliente != "") {
            Text(modifier = Modifier.padding(0.dp, 3.dp),
                text = buildString {
                    if (item.vestuario.isNotBlank()) {
                        append("${item.vestuario} - ")
                    }
                    append(item.nombreSubCliente)
                    if (item.isBorrado) {
                        append(" (Borrado)")
                    }
                },
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Gray
            )
        }

        Column(modifier = Modifier
            .background(Color.Transparent)
            .padding(0.dp), verticalArrangement = Arrangement.Top) {
            //if (!group){
            //    item.listadoPrenda.forEach { prenda ->
            //        PrendaItem(prenda)
            //        Spacer(modifier = Modifier.height(4.dp))
            //    }
            //}
            //else{
            //    var list=item.listadoPrenda.sortedWith(compareBy({it.nombrePrenda},{it.nombreModeloPrenda}, {it.talla}))
            //    var name:String=""
            //    var model:String=""
            //    var size:String=""
            //    list.forEach{ prenda ->
            //        if(name!=prenda.nombrePrenda || model!=prenda.nombreModeloPrenda || size!=prenda.talla){
            //            name=prenda.nombrePrenda
            //            model=prenda.nombreModeloPrenda
            //            size=prenda.talla
            //            var total=item.listadoPrenda.count { it.nombrePrenda==name && it.nombreModeloPrenda==model && it.talla==size }
            //            PrendaItem(prenda, total)
            //            Spacer(modifier = Modifier.height(4.dp))
            //        }
            //    }
            //}
            ListPrendra(items = item.listadoPrenda.filter { prenda -> !prenda.isBorrado }.toList(), group)
       }
    }
}

@Composable
fun PrendaItem( item: Prenda, total: Int = 0 /*, onSelected: () -> Unit */) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFFFFFF)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier
            .size(34.dp)
            .padding(0.dp)
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
        var texto = ""
        texto = if (total == 0) {
            if(item.idPrenda > 0){
                "${item.idPrenda} - ${item.nombrePrenda} - ${item.talla}"
            }else{
                "${item.talla}"
            }
        } else {
            "${item.nombreModeloPrenda} - ${item.talla}"
        }
        if (total == 0) {
             texto = "${texto} \nTag: ${item.tag}"
        }
        Column(modifier = Modifier
            .weight(1f)
            .padding(start = 10.dp, top = 0.dp, end = 10.dp, bottom = 0.dp)) {

            Row(modifier = Modifier
                .fillMaxWidth()
            ) {
            Text(modifier = Modifier
                .weight(1f)
                .padding(0.dp, 5.dp),
                text = texto,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colors.onSurface,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start
            )
            if(total > 0){
                Text(modifier = Modifier
                    .padding(0.dp, 5.dp)
                    .background(Color.Transparent),
                    text = "Total: $total",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colors.onSurface
                )
            }
            }
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
                            Prenda(0, "", "M", "QWERTY123456QWERTY123456", false, false, true),
                            Prenda(0, "", "M", "ASDFGH567890QWERTY123456", false, false, true),
                            Prenda(0, "", "S", "ASDFGH567890QWERTY123456", false, false, true)
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
                            Prenda(1, "Pantalón", "XXL", "QWERTY123456QWERTY123456", true, false, true),
                            Prenda(2, "Camisa", "XXL", "ASDFGH567890QWERTY123456", true, true, true)
                        )
                    ),
                    PrendaSubCliente(
                        0,
                        "Subcliente 1.2",
                        mutableListOf(
                            Prenda(3, "Chaqueta", "S", "", true, false, true)
                        )
                    ),
                    PrendaSubCliente(
                        0,
                        "Subcliente 1.3",
                        mutableListOf(
                            Prenda(4, "Bata", "L", "QWERTY123456QWERTY123456", true, false, true)
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
                            Prenda(5, "Pantalón", "M", "QWERTY123456QWERTY123456", true, true, true)
                        )
                    ),
                    PrendaSubCliente(
                        0,
                        "Subcliente 2.2",
                        mutableListOf(
                            Prenda(6, "Bata", "M", "QWERTY123456QWERTY123456", true, false, true),
                            Prenda(7, "Bata", "L", "ASDFGH567890QWERTY123456", true, true, true)
                        )
                    )
                )
            )
        )
    }
}