package com.lafabricadesoftware.rfidlaundry.presentation.asignacion_prendas.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lafabricadesoftware.rfidlaundry.R
import com.lafabricadesoftware.rfidlaundry.presentation.asignacion_prendas.AsignacionPrendasEvent
import com.lafabricadesoftware.rfidlaundry.presentation.asignacion_prendas.AsignacionPrendasState
import com.lafabricadesoftware.rfidlaundry.presentation.asignacion_prendas.AsignacionPrendasViewModel

@Composable
fun BottomBar(
    asignacionPrendasViewModel: AsignacionPrendasViewModel,
    uiState: State<AsignacionPrendasState>
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
//                enabled = uiState.value.enableMovementsButton,
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.white_66a), contentColor = colorResource(R.color.lfds_primary_900)),
                onClick = {
                    asignacionPrendasViewModel.onEvent(AsignacionPrendasEvent.ShowAssignmentInitialDialog(true))
                }) {
                Text(text = stringResource(R.string.btn_assign_garments), fontSize = 18.sp, fontWeight = FontWeight.Medium)
            }
        }
    }
}
