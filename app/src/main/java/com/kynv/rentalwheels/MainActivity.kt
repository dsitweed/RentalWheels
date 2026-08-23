package com.kynv.rentalwheels

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kynv.rentalwheels.navigation.AppNavigation
import com.kynv.rentalwheels.navigation.AppRoute

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            App()
        }
    }
}

@Composable
fun App(
    viewModel: AppViewModel = viewModel()
) {
    when (val state = viewModel.appState) {
        AppState.Loading -> {
            // Splash/loading screen
        }

        AppState.NoInternet -> {
            // NoInternetScreen()
        }

        is AppState.Ready -> {
            AppNavigation(
                initialRoute = state.initialRoute,
            )
        }
    }
}
