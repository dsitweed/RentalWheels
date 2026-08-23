package com.kynv.rentalwheels.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.kynv.rentalwheels.features.auth.LoginScreen
import com.kynv.rentalwheels.features.profile.ProfileScreen

@Composable
fun AppNavigation(
    initialScreen: AppRoute
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = initialScreen
    ) {
        composable<AppRoute.Login> {
            LoginScreen()
        }

        composable<AppRoute.Profile> { backStackEntry ->
            val profileRoute: AppRoute.Profile = backStackEntry.toRoute<AppRoute.Profile>()
            ProfileScreen(
                userId = profileRoute.userId
            )
        }
    }
}