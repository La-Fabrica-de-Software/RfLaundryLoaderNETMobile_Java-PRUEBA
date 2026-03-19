package com.lafabricadesoftware.rfidlaundry.presentation.lectura_prendas.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lafabricadesoftware.rfidlaundry.R
import com.lafabricadesoftware.rfidlaundry.presentation.lectura_prendas.LecturaPrendasEvent
import com.lafabricadesoftware.rfidlaundry.presentation.lectura_prendas.LecturaPrendasState
import com.lafabricadesoftware.rfidlaundry.presentation.lectura_prendas.LecturaPrendasViewModel

@Composable
fun BottomBar(
    lecturaPrendasViewModel: LecturaPrendasViewModel, uiState: State<LecturaPrendasState>
) {
    BottomAppBar(
        elevation = AppBarDefaults.BottomAppBarElevation,
        backgroundColor = colorResource(R.color.lfds_primary_900),
        contentColor = colorResource(R.color.lfds_secondary_500)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Button(modifier = Modifier
                .padding(10.dp, 0.dp)
                .align(Alignment.CenterHorizontally),
                enabled = uiState.value.enableMovementsButton,
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.white_66a), contentColor = colorResource(R.color.lfds_primary_900)),
                onClick = { lecturaPrendasViewModel.onEvent(LecturaPrendasEvent.ShowMovementsDialog(true)) }) {
                Text(text = "Generar movimientos", fontSize = 18.sp, fontWeight = FontWeight.Medium)
            }
        }
    }
}

//fun BottomBar(uiState: LecturaPrendasState) {
//    Card(
//        modifier = Modifier,
//        backgroundColor = MaterialTheme.colors.primarySurface,
//        shape = RectangleShape,
//        elevation = AppBarDefaults.BottomAppBarElevation * 2
//    ) {
//        Column(modifier = Modifier.padding(5.dp)) {
//            Button(modifier = Modifier
//                .padding(10.dp, 0.dp)
//                .align(Alignment.CenterHorizontally),
//                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White, contentColor = MaterialTheme.colors.primarySurface),
//                onClick = { /*TODO*/ }) {
//                Text(text = "Generar movimientos", fontSize = 16.sp)
//            }
////            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
////                Text("Total: ${uiState.total}", color = Color.LightGray)
////                Text("Únicas: ${uiState.unique}", color = Color.LightGray)
////                Text("Por verificar: ${uiState.toBeChecked}", color = Color.LightGray)
////            }
//        }
//    }
//}

//fun BottomBar(
//    uiState: LecturaPrendasState
//) {
//    BottomAppBar(
//        elevation = 5.dp,
//        backgroundColor = MaterialTheme.colors.primary,
//        contentColor = MaterialTheme.colors.onPrimary
//    ) {
//        Row(modifier =
//        Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceEvenly
//        ) {
//            Text("Total: ${uiState.total}")
//            Text("Únicas: ${uiState.unique}")
//            Text("Por verificar: ${uiState.toBeChecked}")
////            Text("Tiempo: ${uiState.totalTime}s")
//        }
//    }
//}