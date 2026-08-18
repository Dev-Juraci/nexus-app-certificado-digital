package com.devsjura.file_apps.nexus_cdnuvem.others

sealed class AuthState {

    object Idle : AuthState()
    object loadingAuth : AuthState()
    object sucessAuth : AuthState()
    data class Error(val msgError: String) : AuthState()

}