package com.kynv.rentalwheels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

sealed interface AppState {
    data object Loading : AppState
    data object NoInternet : AppState
    data object Onboarding : AppState
    data object Login : AppState
    data object Home : AppState
}

class AppViewModel : ViewModel() {
    var appState by mutableStateOf<AppState>(AppState.Loading)
        private set

    init {
    }

    private fun initialize() {
        viewModelScope.launch {
            val authJob = async(Dispatchers.IO) {
                checkAuthStatus()
            }

            val networkJob = async(Dispatchers.IO) {
                isNetworkConnected()
            }

            val isLoggedIn = authJob.await()
            val isConnected = networkJob.await()

            appState = when {
                !isConnected -> AppState.NoInternet
                isLoggedIn -> AppState.Home
                else -> AppState.Onboarding
            }
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