package com.devsjura.file_apps.nexus_cdnuvem.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devsjura.file_apps.nexus_cdnuvem.others.AuthState
import com.devsjura.file_apps.nexus_cdnuvem.others.IdentificadorUtils
import com.devsjura.file_apps.nexus_cdnuvem.others.TipoIdentificador
import com.devsjura.file_apps.nexus_cdnuvem.repository.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel(private val authViewModel: AuthRepository = AuthRepository()) : ViewModel() {

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

        _stateRegistration.value = AuthState.loadingAuth

        viewModelScope.launch {

            try {
                authViewModel.signUpUsers(
                    namesAuViMo,
                    cpfAuViMo,
                    emailAuViMo,
                    phoneAuViMo,
                    passwordAuViMo
                )
                _stateRegistration.value = AuthState.sucessAuth
            } catch (e: Exception) {
                _stateRegistration.value = AuthState.Error(e.message ?: "Erro ao cadastrar")
            }


        }


    }

    fun login(identificador: String, userSecure: String, typeAuthLogin: TipoIdentificador) {
        _loginState.value = AuthState.loadingAuth

        viewModelScope.launch {
            try {
                val typeAuth = IdentificadorUtils.detectType(identificador)
                authViewModel.login(identificador, userSecure, typeAuth)
                _loginState.value = AuthState.sucessAuth

            } catch (e: Exception) {
                _loginState.value = AuthState.Error(e.message ?: "Credenciais inválidas")
            }
        }


    }


}