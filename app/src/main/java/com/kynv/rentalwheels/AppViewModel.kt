package com.kynv.rentalwheels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kynv.rentalwheels.navigation.AppRoute
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

sealed interface AppState {
    data object Loading : AppState
    data object NoInternet : AppState
    data class Ready(val initialRoute: AppRoute) : AppState
}

class AppViewModel : ViewModel() {
    var appState by mutableStateOf<AppState>(AppState.Loading)
        private set

    init {
        initialize()
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
                isLoggedIn -> {
                    AppState.Ready(AppRoute.Home)
                }
                else -> {
                    AppState.Ready(AppRoute.Onboarding)
                }
            }
        }
    }

    private suspend fun checkAuthStatus(): Boolean {
        return false
    }

    private suspend fun isNetworkConnected(): Boolean {
        return true
    }
}