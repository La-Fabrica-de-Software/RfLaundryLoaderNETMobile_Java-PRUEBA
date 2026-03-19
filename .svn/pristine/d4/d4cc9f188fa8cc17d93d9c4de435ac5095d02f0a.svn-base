package com.lafabricadesoftware.rfidlaundry.presentation

import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.lafabricadesoftware.rfidlaundry.presentation.asignacion_prendas.AsignacionPrendasViewModel
import com.lafabricadesoftware.rfidlaundry.presentation.lectura_prendas.LecturaPrendasViewModel
import com.lafabricadesoftware.rfidlaundry.presentation.navigation.SplashNavigation
import com.lafabricadesoftware.rfidlaundry.presentation.ui.theme.RFIDLaundryTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val inventoryFlag = 1
    private var triggerPressed = false

    private val lecturaPrendasViewModel: LecturaPrendasViewModel by viewModels()
    private val asignacionPrendasViewModel: AsignacionPrendasViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RFIDLaundryTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    SplashNavigation(lecturaPrendasViewModel, asignacionPrendasViewModel)
                }
            }
        }
    }
    override fun onDestroy() {
        lecturaPrendasViewModel.freeReader()
        super.onDestroy()
    }

    //region KEY PRESS

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (!triggerPressed) {
            if (keyCode == 139 || keyCode == 280) {
                println("----- onKeyDown. KeyCode: $keyCode -----")
                triggerPressed = false
                asignacionPrendasViewModel.startScan()
            } else if (keyCode == 293) {
                println("----- onKeyDown. KeyCode: $keyCode -----")
                triggerPressed = true
                lecturaPrendasViewModel.startInventory()
//                asignacionPrendasViewModel.startInventory()
            }
        }
        return super.onKeyDown(keyCode, event)
    }
    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == 293) {
            println("----- onKeyUp. KeyCode: $keyCode -----")
            if (inventoryFlag == 1) {
                lecturaPrendasViewModel.stopInventory()
//                asignacionPrendasViewModel.stopInventory()
            }
            triggerPressed = false
        }
        return super.onKeyUp(keyCode, event)
    }

    //endregion
}