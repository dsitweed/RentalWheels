package com.kynv.rentalwheels.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.kynv.rentalwheels.features.auth.LoginScreen
import com.kynv.rentalwheels.features.profile.ProfileScreen

@Composable
fun AppNavigation(
    initialRoute: AppRoute,
    navController: NavHostController = rememberNavController(),
) {

    NavHost(
        navController = navController,
        startDestination = initialRoute
    ) {
        composable<AppRoute.Onboarding> {
            // TODO: Implement OnboardingScreen
            LoginScreen()
        }

        composable<AppRoute.Login> {
            LoginScreen()
        }

        composable<AppRoute.Home> {
            // TODO: Implement HomeScreen
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