package com.kynv.rentalwheels

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kynv.rentalwheels.ui.theme.RentalwheelsTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        var isReady = false
        splashScreen.setKeepOnScreenCondition { !isReady }

        setContent {
            App()
//            RentalwheelsTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
//            }
        }

        lifecycleScope.launch {
            val authJob = async(Dispatchers.IO) { checkAuthStatus() }
            val networkJob = async(Dispatchers.IO) { isNetworkConnected() }

            val isLoggedIn = authJob.await()
            val isConnected = networkJob.await()

            isReady = true

            when {
                !isConnected -> showNoInternetDialog()
                isLoggedIn -> navigateTo(Home)
                else -> checkAndNavigateToOnboarding()
            }

            isReady = true
        }
    }

    private suspend fun checkAuthStatus(): Boolean {
        return false;
    }

    private fun isNetworkConnected(): Boolean {
        return false;
    }

    private fun showNoInternetDialog() {

    }

    private fun checkAndNavigateToOnboarding() {

    }

    private fun navigateTo(destinationClass: Class<*>) {

    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun App() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "Home"
    ) {
        composable("Home") {
//            HomeScreen()
        }

        composable("onboarding") {
//            OnboardingScreen()
        }

        composable("login") {
//            LoginScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RentalwheelsTheme {
        Greeting("Android")
    }
}