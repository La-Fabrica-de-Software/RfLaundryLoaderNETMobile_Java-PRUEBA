import androidx.compose.foundation.layout.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.platform.LocalContext;
import androidx.compose.ui.tooling.preview.Preview;
import androidx.appcompat.app.AppCompatDelegate;
import android.app.Activity;

@Composable
fun ConfiguracionScreen() {
    val activity = LocalContext.current as? Activity
    Column(modifier = Modifier.fillMaxSize()) {
        // Your UI elements for the configuration screen
    }

    // Assuming you have a radio button for English and Spanish
    RadioButton(checked = true, onClick = { 
        AppCompatDelegate.setApplicationLocales(...)  // your locale change code
        activity?.recreate() 
    })
    RadioButton(checked = false, onClick = { 
        AppCompatDelegate.setApplicationLocales(...)  // your locale change code
        activity?.recreate() 
    })
}

@Preview(showBackground = true)
@Composable
fun PreviewConfiguracionScreen() {
    ConfiguracionScreen()
}