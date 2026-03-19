package com.lafabricadesoftware.rfidlaundry.presentation.common.components

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.lafabricadesoftware.rfidlaundry.R

@Composable
fun LoadingDialog(text: String = "") {
    val displayText = text.ifEmpty { stringResource(R.string.please_wait) }
    Dialog(
        onDismissRequest = { /*TODO*/ }
    ) {
        Surface(modifier = Modifier.background(Color.Transparent).width(200.dp),
            elevation = 10.dp,
            shape = RoundedCornerShape(8)
        ) {
            Column(modifier = Modifier
                    .padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProgressIndicatorLoading(60.dp, colorResource(R.color.lfds_primary_900))
                Spacer(modifier = Modifier.height(15.dp))
                Text(modifier = Modifier
                        .fillMaxWidth(),
                    text = displayText,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                )
            }
        }
    }
}

@Composable
fun ProgressIndicatorLoading(progressIndicatorSize: Dp, progressIndicatorColor: Color) {

    val infiniteTransition = rememberInfiniteTransition()

    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 600
            }
        )
    )

    CircularProgressIndicator(
        progress = 1f,
        modifier = Modifier
            .size(progressIndicatorSize)
            .rotate(angle)
            .border(
                5.dp, brush = Brush.sweepGradient(
                    listOf(
                        Color.White, progressIndicatorColor.copy(alpha = 0.1f), progressIndicatorColor
                    )
                ), shape = CircleShape
            ),
        strokeWidth = 1.dp,
        color = Color.White
    )
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xEFEFEFEF, widthDp = 400, heightDp = 600)
fun Preview() {
    LoadingDialog()
}
