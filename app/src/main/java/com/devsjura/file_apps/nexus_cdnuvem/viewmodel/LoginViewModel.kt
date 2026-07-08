package com.devsjura.file_apps.nexus_cdnuvem.viewmodel

import androidx.lifecycle.ViewModel

sealed class LoginStates {
    object loading : LoginStates()
    data class Success(val msgSucess: String) : LoginStates()
    data class Error(val msgError: String) : LoginStates()
    data class BlockTmp(val remainingSeconds: Int) : LoginStates()
}


class LoginViewModel : ViewModel() {
}