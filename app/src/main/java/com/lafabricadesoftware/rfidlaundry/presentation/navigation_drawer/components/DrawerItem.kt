package com.lafabricadesoftware.rfidlaundry.presentation.navigation_drawer.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lafabricadesoftware.rfidlaundry.presentation.navigation.Screens

@Composable
fun DrawerItem(item: Screens, selected: Boolean, onItemClick: (Screens) -> Unit){
    val background = if(selected) android.R.color.darker_gray else android.R.color.transparent
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(item) }
            .height(45.dp)
            .background(colorResource(id = background))
            .padding(start = 10.dp)
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = stringResource(item.title),
            modifier = Modifier.height(24.dp).width(24.dp)
        )

        Spacer(modifier = Modifier.width(7.dp))
        Text(
            text = stringResource(item.title),
            fontSize = 16.sp,
            color = Color.Black
        )
    }
}
