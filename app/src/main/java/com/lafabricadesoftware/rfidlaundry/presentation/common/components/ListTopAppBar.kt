package com.lafabricadesoftware.rfidlaundry.presentation.common.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.lafabricadesoftware.rfidlaundry.presentation.common.components.model.ActionItem

@Composable
internal fun ListTopAppBar(
    actionItems: List<ActionItem>
) {
    TopAppBar(
        title = { /*...*/ },
        navigationIcon = {/*...*/},
        actions = {
            val (icons, options) = actionItems.partition { true }

            icons.forEach {
                IconButton(onClick = it.action) {
                    Icon(imageVector = it.icon, contentDescription = it.name)
                }
            }

            val (isExpanded, setExpanded) = remember { mutableStateOf(false) }
            OverflowMenuAction(isExpanded, setExpanded, options)
        }
    )
}