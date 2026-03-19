package com.lafabricadesoftware.rfidlaundry.presentation.common.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Filter
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.lafabricadesoftware.rfidlaundry.presentation.common.components.model.ActionItem

@Composable
fun OverflowMenuAction(
    expanded: Boolean,
    setExpanded: (Boolean) -> Unit,
    options: List<ActionItem>
) {

//    IconButton(onClick = { setExpanded(true) }) {
//        Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "Ver más")

//        DropdownMenu(
//            expanded = expanded,
//            onDismissRequest = { setExpanded(false) },
//            offset = DpOffset(x = 0.dp, y = 4.dp)
//        ) {
//            options.forEach { option ->
//                DropdownMenuItem(
//                    onClick = {
//                        option.action()
//                        setExpanded(false)
//                    }
//                ) {
////                    Icon(imageVector = option.icon, contentDescription = "")
//                    Icon(modifier = Modifier.padding(0.dp,0.dp,10.dp,0.dp), imageVector = option.icon, contentDescription = "")
//                    Text(text = option.name)
//                }
//            }
//        }
//    }

}