package com.lafabricadesoftware.rfidlaundry.presentation.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun SuperDialog(
    showTitle: Boolean = true,
    title: String = "Información",
    titleAlign: TextAlign = TextAlign.Start,
    showTitleSpacer: Boolean = true,
    text: String = "Lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum.",
    showButtonsSpacer: Boolean = true,
    showCloseButtonOnly: Boolean = false,
    dismissOnBackPress: Boolean = false,
    dismissOnClickOutside: Boolean = false,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            dismissOnBackPress = dismissOnBackPress,
            dismissOnClickOutside = dismissOnClickOutside
        )
    ) {
        Surface(
            shape = RoundedCornerShape(size = 10.dp)
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
            ) {

                if (showTitle) {
                    Row(modifier = Modifier.padding(0.dp)) {
                        Text(modifier = Modifier.fillMaxWidth(),
                            text = title,
                            textAlign = titleAlign,
                            style = MaterialTheme.typography.h6
                        )
                    }
                    if (showTitleSpacer) {
                        Row(modifier = Modifier.padding(0.dp, 5.dp, 0.dp, 0.dp)) {
                            Spacer(modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(Color.LightGray)
                            )
                        }
                    }
                }
                Row(modifier = Modifier
                    .padding(0.dp, if (showTitle) { 10.dp } else { 0.dp }, 0.dp, 0.dp)) {
                    Text(modifier = Modifier.fillMaxWidth(),
                        text = text,
                    )
                }
                if (showButtonsSpacer) {
                    Row(modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 0.dp)) {
                        Spacer(modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color.LightGray)
                        )
                    }
                }
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 5.dp, 0.dp, 0.dp),
                    horizontalArrangement = Arrangement.End) {
                    if (showCloseButtonOnly) {
                        TextButton(modifier = Modifier.height(35.dp),
                            onClick = { onDismiss() }) {
                            Text(text = "Cerrar")
                        }
                    } else {
                        TextButton(modifier = Modifier.height(35.dp),
                            onClick = { onDismiss() }) {
                            Text(text = "Cancelar")
                        }
                        TextButton(modifier = Modifier.height(35.dp),
                            onClick = { onConfirm() }) {
                            Text(text = "Aceptar")
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xEFEFEFEF, widthDp = 400, heightDp = 600)
fun QuestionDialogPreview() {
//    SuperDialog()
}