package com.kynv.rentalwheels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.kynv.rentalwheels.navigation.AppRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface AppState {
    data object Loading : AppState
    data object NoInternet : AppState
    data class Ready(val initialRoute: AppRoute) : AppState
}

@HiltViewModel
class AppViewModel @Inject constructor(
    val application: Application
) : ViewModel() {
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
        // TODO: Compose UI -> ViewModel -> Repository -> Firebase
        //  CheckAuthStatus logic need fix (AuthRepository)
        return try {
            FirebaseAuth.getInstance().currentUser != null
        } catch (e: Exception) {
            false
        }
    }

    private fun isNetworkConnected(): Boolean {
        // TODO: not check in ViewModel
        val cm = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return cm.getNetworkCapabilities(cm.activeNetwork)?.let {
            it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
        } ?: false
    }
}