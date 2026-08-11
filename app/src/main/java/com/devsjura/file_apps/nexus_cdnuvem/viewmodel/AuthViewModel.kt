package com.devsjura.file_apps.nexus_cdnuvem.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devsjura.file_apps.nexus_cdnuvem.repository.AuthRepository

sealed class AuthState : ViewModel(){
    object Idle : AuthState()
    object LoadingAuth: AuthState()
    object successAuth: AuthState()
    data class Error(val msgErro : String): AuthState()
}

class AuthViewModel (private val authViewModel: AuthRepository) {

    private val _stateRegistration = MutableLiveData<AuthState>(AuthState.Idle)
    val stateRegistration : LiveData<AuthState> = _stateRegistration

}