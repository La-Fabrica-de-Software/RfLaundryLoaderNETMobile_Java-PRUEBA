package com.lafabricadesoftware.rfidlaundry.presentation.busqueda_prendas.seleccion_prenda.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Help
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
import androidx.navigation.NavHostController
import com.lafabricadesoftware.rfidlaundry.domain.model.MasterPrendas
import com.lafabricadesoftware.rfidlaundry.domain.model.ui.BusquedaPrendaPrenda

@Composable
fun PrendaItem(navController: NavHostController, item: BusquedaPrendaPrenda) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFFFFFF)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier
            .size(46.dp)
            .padding(0.dp)
            .background(Color(0xFF4CAF50)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(Icons.Filled.Person, "", tint = Color.White)
        }
        Column(modifier = Modifier
            .weight(1f)
            .padding(start = 10.dp, top = 0.dp, end = 10.dp, bottom = 0.dp)
        ) {
            Text(modifier = Modifier,
                text = item.prenda.codigoTAG,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colors.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start
            )
            Text(modifier = Modifier,
                text = item.prenda.descrip,
                fontSize = 16.sp,
                fontWeight = FontWeight.Thin,
                color = MaterialTheme.colors.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start
            )
        }
    }
}
//
//@Preview(showBackground = true, widthDp = 400, heightDp = 600)
//@Composable
//fun Preview() {
//    Column() {
//        Row (
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(Color(0xFFFFFFFF)),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Column(modifier = Modifier
//                .size(46.dp)
//                .padding(0.dp)
//                .background(Color(0xFF4CAF50)),
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Icon(Icons.Filled.HelpOutline, "", tint = Color.White)
//            }
//            Column(modifier = Modifier
//                .weight(1f)
//                .padding(start = 10.dp, top = 0.dp, end = 10.dp, bottom = 0.dp)
//            ) {
//                Text(modifier = Modifier,
//                    text = "EWRWER8W9R89WERW9R0WR",
//                    fontSize = 16.sp,
//                    fontWeight = FontWeight.Normal,
//                    color = MaterialTheme.colors.onSurface,
//                    maxLines = 1,
//                    overflow = TextOverflow.Ellipsis,
//                    textAlign = TextAlign.Start
//                )
//                Text(modifier = Modifier,
//                    text = "DESCRIPCION",
//                    fontSize = 16.sp,
//                    fontWeight = FontWeight.Thin,
//                    color = MaterialTheme.colors.onSurface,
//                    maxLines = 1,
//                    overflow = TextOverflow.Ellipsis,
//                    textAlign = TextAlign.Start
//                )
//            }
//        }
//        Spacer(modifier = Modifier.height(20.dp))
//        Row (
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(Color(0xFF4CAF50)),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Column(modifier = Modifier
//                .size(46.dp)
//                .padding(0.dp)
//                .background(Color(0xFF4CAF50)),
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Icon(Icons.Outlined.CheckCircle, "", tint = Color.White)
//            }
//            Column(modifier = Modifier
//                .weight(1f)
//                .padding(start = 10.dp, top = 0.dp, end = 10.dp, bottom = 0.dp)
//            ) {
//                Text(modifier = Modifier,
//                    text = "EWRWER8W9R89WERW9R0WR",
//                    fontSize = 16.sp,
//                    fontWeight = FontWeight.Normal,
//                    color = Color.White,
//                    maxLines = 1,
//                    overflow = TextOverflow.Ellipsis,
//                    textAlign = TextAlign.Start
//                )
//                Text(modifier = Modifier,
//                    text = "DESCRIPCION Qwewersdsfdgkd",
//                    fontSize = 16.sp,
//                    fontWeight = FontWeight.Thin,
//                    color = Color.White,
//                    maxLines = 1,
//                    overflow = TextOverflow.Ellipsis,
//                    textAlign = TextAlign.Start
//                )
//            }
//        }
//    }
//}