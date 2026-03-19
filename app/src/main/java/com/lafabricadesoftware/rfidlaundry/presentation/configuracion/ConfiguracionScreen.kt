package com.lafabricadesoftware.rfidlaundry.presentation.configuracion

import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.material.OutlinedTextField
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lafabricadesoftware.rfidlaundry.R
import com.lafabricadesoftware.rfidlaundry.presentation.common.components.DefaultRadioButton
import com.lafabricadesoftware.rfidlaundry.presentation.configuracion.components.TopBarConfiguracion
import com.lafabricadesoftware.rfidlaundry.presentation.lectura_prendas.LecturaPrendasEvent
import kotlinx.coroutines.CoroutineScope

@Composable
fun ConfiguracionScreen(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    viewModel: ConfiguracionViewModel = hiltViewModel()
) {

    val serverState = viewModel.server.value
    val portState = viewModel.port.value
    val databaseState = viewModel.database.value
    val usernameState = viewModel.username.value
    val passwordState = viewModel.password.value
    val clientIdState = viewModel.clientId.value
    val workstationIdState = viewModel.workstationId.value
    var onlineState=viewModel.onlineOnly.value
    var readBarcodeState=viewModel.readBarcode.value

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.value.showMessage) {
        Toast.makeText(LocalContext.current, uiState.value.message, Toast.LENGTH_SHORT).show()
        viewModel.onEvent(ConfiguracionEvent.OnMessageShown)
    }

    if (!viewModel.loaded){
        viewModel.onEvent(ConfiguracionEvent.OnInit)
        viewModel.onEvent(ConfiguracionEvent.OnConfigurationLoaded)
    }

    Column(modifier = Modifier.background(Color(0xEEEEEEEE))) {
        TopBarConfiguracion(scope, scaffoldState, viewModel)

        Column(modifier = Modifier.weight(1f).padding(10.dp).verticalScroll(rememberScrollState())) {

            Text(modifier = Modifier.padding(5.dp, 5.dp, 5.dp, 0.dp), text = stringResource(R.string.db_connection_section), color = MaterialTheme.colors.primary)

            Row(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(modifier = Modifier.fillMaxWidth().padding(5.dp, 5.dp, 5.dp, 0.dp),
                    value = serverState, onValueChange = { viewModel.onEvent(ConfiguracionEvent.EnteredServer(it)) },
                    label = { Text(text = stringResource(R.string.label_server)) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colors.primary)
                )
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(modifier = Modifier.weight(2f).padding(5.dp, 5.dp, 5.dp, 0.dp),
                    value = portState, onValueChange = { viewModel.onEvent(ConfiguracionEvent.EnteredPort(it)) },
                    label = { Text(text = stringResource(R.string.label_port)) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = MaterialTheme.colors.primary),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(modifier = Modifier.weight(5f).padding(5.dp, 5.dp, 5.dp, 0.dp),
                    value = databaseState, onValueChange = { viewModel.onEvent(ConfiguracionEvent.EnteredDatabase(it)) },
                    label = { Text(text = stringResource(R.string.label_database)) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = MaterialTheme.colors.primary)
                )
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(modifier = Modifier.weight(1f).padding(5.dp),
                    value = usernameState,
                    onValueChange = { viewModel.onEvent(ConfiguracionEvent.EnteredUsername(it)) },
                    label = { Text(text = stringResource(R.string.label_username)) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = MaterialTheme.colors.primary)
                )
                OutlinedTextField(modifier = Modifier.weight(1f).padding(5.dp),
                    value = passwordState,
                    onValueChange = { viewModel.onEvent(ConfiguracionEvent.EnteredPassword(it)) },
                    label = { Text(text = stringResource(R.string.label_password)) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = MaterialTheme.colors.primary),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
            }

            Text(modifier = Modifier.padding(5.dp, 10.dp, 5.dp, 0.dp), text = stringResource(R.string.other_settings_section), color = MaterialTheme.colors.primary)

            Row(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(modifier = Modifier.weight(1f).padding(5.dp, 5.dp, 5.dp, 0.dp),
                    value = clientIdState,
                    onValueChange = { viewModel.onEvent(ConfiguracionEvent.EnteredClientId(it)) },
                    label = { Text(text = stringResource(R.string.label_client_id)) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = MaterialTheme.colors.primary),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(modifier = Modifier.weight(1f).padding(5.dp, 5.dp, 5.dp, 0.dp),
                    value = workstationIdState,
                    onValueChange = { viewModel.onEvent(ConfiguracionEvent.EnteredWorkstationId(it)) },
                    label = { Text(text = stringResource(R.string.label_workstation_id)) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = MaterialTheme.colors.primary),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            Row(modifier = Modifier.fillMaxWidth().height(50.dp).padding(5.dp, 15.dp, 5.dp, 5.dp)) {
                Text(modifier = Modifier.weight(1f).padding(top = 5.dp), text = stringResource(R.string.label_online_only))
                Switch(checked = onlineState, onCheckedChange = {viewModel.onEvent(ConfiguracionEvent.SelectedOnlineOnly(it))},
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colors.primary
                    )
                )
            }
            Row(modifier = Modifier.fillMaxWidth().height(50.dp).padding(5.dp, 15.dp, 5.dp, 5.dp)) {
                Text(modifier = Modifier.weight(1f).padding(top = 5.dp), text = stringResource(R.string.label_read_barcode))
                Switch(checked = readBarcodeState, onCheckedChange = {viewModel.onEvent(ConfiguracionEvent.SelectedReadBarcode(it))},
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colors.primary
                    )
                )
            }

            var selectedLanguage by remember {
                val tag = AppCompatDelegate.getApplicationLocales().toLanguageTags()
                mutableStateOf(if (tag.startsWith("es")) "es" else "en")
            }

            Text(
                modifier = Modifier.padding(5.dp, 10.dp, 5.dp, 0.dp),
                text = stringResource(R.string.language_section),
                color = MaterialTheme.colors.primary
            )
            Row(modifier = Modifier.fillMaxWidth().padding(5.dp, 5.dp, 5.dp, 0.dp)) {
                DefaultRadioButton(
                    text = stringResource(R.string.language_english),
                    selected = selectedLanguage == "en",
                    onSelect = {
                        selectedLanguage = "en"
                        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("en"))
                    }
                )
                Spacer(modifier = Modifier.width(16.dp))
                DefaultRadioButton(
                    text = stringResource(R.string.language_spanish),
                    selected = selectedLanguage == "es",
                    onSelect = {
                        selectedLanguage = "es"
                        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("es"))
                    }
                )
            }
        }

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp), horizontalArrangement = Arrangement.Center
        ) {
            Button(modifier = Modifier.padding(end = 10.dp),
                onClick = { viewModel.onEvent(ConfiguracionEvent.OnTest) }) {
                Text(text = stringResource(R.string.btn_test_config))
            }
            Button(modifier = Modifier,
                onClick = { viewModel.onEvent(ConfiguracionEvent.OnClean) }) {
                Text(text = stringResource(R.string.btn_clear_all))
            }
        }
    }
}
