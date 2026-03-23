package com.lafabricadesoftware.rfidlaundry.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.lafabricadesoftware.rfidlaundry.presentation.asignacion_prendas.AsignacionPrendasViewModel
import com.lafabricadesoftware.rfidlaundry.presentation.buscar_prenda.BuscarPrendaViewModel
import com.lafabricadesoftware.rfidlaundry.presentation.lectura_prendas.LecturaPrendasViewModel
import com.lafabricadesoftware.rfidlaundry.presentation.navigation.SplashNavigation
import com.lafabricadesoftware.rfidlaundry.presentation.ui.theme.RFIDLaundryTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val inventoryFlag = 1
    private var triggerPressed = false

    private val lecturaPrendasViewModel: LecturaPrendasViewModel by viewModels()
    private val asignacionPrendasViewModel: AsignacionPrendasViewModel by viewModels()
    private val buscarPrendaViewModel: BuscarPrendaViewModel by viewModels()

    private val bluetoothPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.values.all { it }
        if (allGranted) {
            println("+++++ Bluetooth permissions granted +++++")
        } else {
            println("----- Bluetooth permissions denied -----")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestBluetoothPermissionsIfNeeded()
        setContent {
            RFIDLaundryTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    SplashNavigation(lecturaPrendasViewModel, asignacionPrendasViewModel, buscarPrendaViewModel)
                }
            }
        }
    }

    private fun requestBluetoothPermissionsIfNeeded() {
        // Android 12+ (API 31+) requires BLUETOOTH_SCAN and BLUETOOTH_CONNECT at runtime.
        // The Chainway DeviceAPI SDK uses Bluetooth services internally even on UART-based
        // devices like the C72. Without these permissions, the SDK fails to initialize on
        // Android 13 and the RFID/barcode hardware becomes unresponsive.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val permissionsToRequest = mutableListOf<String>()
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN)
                != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(Manifest.permission.BLUETOOTH_SCAN)
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT)
                != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(Manifest.permission.BLUETOOTH_CONNECT)
            }
            if (permissionsToRequest.isNotEmpty()) {
                bluetoothPermissionLauncher.launch(permissionsToRequest.toTypedArray())
            }
        }
    }

    override fun onDestroy() {
        if (isFinishing) {
            lecturaPrendasViewModel.freeReader()
            buscarPrendaViewModel.freeReader()
        }
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