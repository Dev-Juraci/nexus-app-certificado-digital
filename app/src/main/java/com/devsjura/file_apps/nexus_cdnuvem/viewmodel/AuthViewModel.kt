package com.devsjura.file_apps.nexus_cdnuvem.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devsjura.file_apps.nexus_cdnuvem.repository.AuthRepository
import kotlinx.coroutines.launch

sealed class AuthState {
    object Idle : AuthState()
    object LoadingAuth : AuthState()
    object successAuth : AuthState()
    data class Error(val msgErro: String) : AuthState()
}

class AuthViewModel(private val authViewModel: AuthRepository) : ViewModel() {

    private val _stateRegistration = MutableLiveData<AuthState>(AuthState.Idle)
    val stateRegistration: LiveData<AuthState> = _stateRegistration

    private val _loginState = MutableLiveData<AuthState>(AuthState.Idle)
    val loginState = MutableLiveData<AuthState>(AuthState.Idle)

    fun registerUsers(
        namesAuViMo: String,
        emailAuViMo: String,
        cpfAuViMo: String,
        phoneAuViMo: String,
        passwordAuViMo: String,
    ) {

        _stateRegistration.value = AuthState.LoadingAuth

        viewModelScope.launch {

            try {
                authViewModel.signUpUsers(
                    namesAuViMo,
                    emailAuViMo,
                    cpfAuViMo,
                    phoneAuViMo,
                    passwordAuViMo
                )
                _stateRegistration.value = AuthState.successAuth
            } catch (e: Exception) {
                _stateRegistration.value = AuthState.Error(e.message ?: "Erro ao cadastrar")
            }


        }


    }

}