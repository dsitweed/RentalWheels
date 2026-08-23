package com.kynv.rentalwheels.navigation

import kotlinx.serialization.Serializable

sealed interface AppRoute {
    @Serializable
    data object Home : AppRoute
    @Serializable
    data object Login : AppRoute
    @Serializable
    data object Onboarding : AppRoute
    @Serializable
    data class Profile(val userId: String) : AppRoute
}