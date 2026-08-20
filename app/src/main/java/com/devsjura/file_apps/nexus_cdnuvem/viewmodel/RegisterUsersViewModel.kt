package com.devsjura.file_apps.nexus_cdnuvem.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devsjura.file_apps.nexus_cdnuvem.others.AuthState
import com.devsjura.file_apps.nexus_cdnuvem.repository.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepositoryRegister: AuthRepository = AuthRepository()) :
    ViewModel() {

    private val _stateRegistration = MutableLiveData<AuthState>(AuthState.Idle)
    val stateRegistration: LiveData<AuthState> = _stateRegistration


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
                authRepositoryRegister.signUpUsers(
                    namesAuViMo,
                    cpfAuViMo,
                    emailAuViMo,
                    passwordAuViMo,
                    phoneAuViMo,

                    )
                _stateRegistration.value = AuthState.sucessAuth
            } catch (e: Exception) {
                _stateRegistration.value = AuthState.Error(e.message ?: "Erro ao cadastrar")
            }


        }


    }

}