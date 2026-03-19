package com.lafabricadesoftware.rfidlaundry.presentation.lectura_prendas.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.lafabricadesoftware.rfidlaundry.R
import com.lafabricadesoftware.rfidlaundry.domain.model.MasterTipoAntena

//@Preview(showBackground = true, widthDp = 400, heightDp = 600)
@Composable
fun MovementsDialog(
    movementsList: List<MasterTipoAntena>,
    onDismiss: () -> Unit,
    onConfirm: (antennaId: Int) -> Unit
) {

    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        Surface(
            modifier = Modifier.height(350.dp), shape = RoundedCornerShape(size = 10.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Row() {
                    Column() {
                        Text(
                            modifier = Modifier.fillMaxWidth().padding(10.dp),
                            text = "Seleccione un movimiento",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.subtitle1
                        )
                        Spacer(
                            modifier = Modifier.fillMaxWidth().height(1.dp).background(Color.LightGray)
                        )
                    }
                }
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState()).weight(1f).padding(15.dp, 0.dp)
                ) {
                    Spacer(modifier = Modifier.height(5.dp))
                    movementsList.forEach {
                        Button(modifier = Modifier.fillMaxWidth().padding(0.dp).defaultMinSize(minHeight = 45.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = colorResource(R.color.lfds_primary_700), contentColor = Color.White
                            ),
                            onClick = { onConfirm(it.Id) }) {
                            Text(textAlign = TextAlign.Center, text = it.descripcion.toString())
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                }
                Column() {
                    Spacer(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color.LightGray))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp, 0.dp), horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { onDismiss() }) {
                            Text(text = "Cancelar")
                        }
                    }
                }
            }
        }
    }
}