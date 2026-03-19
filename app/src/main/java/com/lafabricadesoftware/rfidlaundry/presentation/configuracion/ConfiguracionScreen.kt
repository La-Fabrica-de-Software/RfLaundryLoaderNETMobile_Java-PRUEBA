import android.app.Activity

// Other existing imports...

@Composable
fun ConfiguracionScreen() {
    val context = LocalContext.current
    val activity = context as? Activity

    // Other existing code...

    // Language radio button onSelect handlers
    onSelect = { 
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("en"))
        activity?.recreate()
    }
    // Other existing code...
}