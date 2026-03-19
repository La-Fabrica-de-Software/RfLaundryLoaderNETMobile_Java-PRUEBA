package com.lafabricadesoftware.rfidlaundry.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.lafabricadesoftware.rfidlaundry.R
import com.lafabricadesoftware.rfidlaundry.presentation.navigation.Screens
import com.lafabricadesoftware.rfidlaundry.presentation.MainViewModel
import com.lafabricadesoftware.rfidlaundry.presentation.lectura_prendas.LecturaPrendasViewModel

@Composable
fun SplashScreen(
    navController: NavHostController,
//    mainViewModel: MainViewModel = hiltViewModel(),
//    lecturaPrendasViewModel: LecturaPrendasViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
//        delay(100)
//        lecturaPrendasViewModel.initReader(1)
//        mainViewModel.testDB()

        navController.popBackStack()
        navController.navigate(Screens.Main.route)
    }
    Splash()
}

@Composable
fun Splash() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = stringResource(R.string.splash_logo))
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.loading),
            color = Color.DarkGray,
            fontSize = 16.sp
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun SplashScreenPreview() {
//    Splash()
//}
