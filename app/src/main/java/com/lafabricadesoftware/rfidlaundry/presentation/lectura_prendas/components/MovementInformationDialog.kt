package com.lafabricadesoftware.rfidlaundry.presentation.lectura_prendas.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.lafabricadesoftware.rfidlaundry.R

//@Preview(showBackground = true, widthDp = 400, heightDp = 600)
@Composable
fun MovementInformationDialog(
    text: String,
    antennaId: Int,
    hideButtons: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (antennaId: Int) -> Unit
) {

    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Surface(
//            modifier = Modifier.height(350.dp),
            shape = RoundedCornerShape(size = 10.dp)
        ) {
            Column(
//                modifier = Modifier.fillMaxSize()
            ) {
                Row() {
                    Column() {
                        Text(
                            modifier = Modifier.fillMaxWidth().padding(10.dp),
                            text = stringResource(R.string.dialog_information),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.subtitle1
                        )
                        Spacer(
                            modifier = Modifier.fillMaxWidth().height(1.dp).background(Color.LightGray)
                        )
                    }
                }
                Column(
//                    modifier = Modifier.verticalScroll(rememberScrollState()).weight(1f).padding(15.dp, 0.dp)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth().padding(10.dp),
                        text = text,
                    )
                }
                Column() {
                    Spacer(
                        modifier = Modifier.fillMaxWidth().height(1.dp).background(Color.LightGray)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp, 0.dp), horizontalArrangement = Arrangement.End
                    ) {
                        if (hideButtons) {
                            TextButton(onClick = { onConfirm(0) }) {
                                Text(text = stringResource(R.string.btn_close))
                            }
                        } else {
                            TextButton(onClick = { onDismiss() }) {
                                Text(text = stringResource(R.string.btn_cancel))
                            }
                            TextButton(onClick = { onConfirm(antennaId) }) {
                                Text(text = stringResource(R.string.btn_accept))
                            }
                        }
                    }
                }
            }
        }
    }
}
