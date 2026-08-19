package com.devsjura.file_apps.nexus_cdnuvem.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devsjura.file_apps.nexus_cdnuvem.model.StatesLogin
import com.devsjura.file_apps.nexus_cdnuvem.others.AuthState
import com.devsjura.file_apps.nexus_cdnuvem.others.IdentificadorUtils
import com.devsjura.file_apps.nexus_cdnuvem.others.LoginAttemptManager
import com.devsjura.file_apps.nexus_cdnuvem.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.getValue
import kotlin.time.Duration.Companion.milliseconds

class LoginViewModel(
    private val authRepositoryLogin: AuthRepository = AuthRepository(),
    private val loginAttemptManager: LoginAttemptManager,
) : ViewModel() {

    private val fbAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private val _loginStateLogin = MutableLiveData<AuthState>(AuthState.Idle)
    val loginStateLogin: LiveData<AuthState> get() = _loginStateLogin
    val loginSucess = MutableLiveData<Boolean>()
    val messageUser = MutableLiveData<StatesLogin>()


    fun checkInitialLock() {
        val remaTime = loginAttemptManager.remainingLockoutTime()
        if (remaTime > 0) {
            startCountdown(remaTime)
        }
    }

    fun startCountdown(startTime: Long) {
        viewModelScope.launch {

            var timeRemaining = startTime

            while (timeRemaining > 0) {
                val minutes = (timeRemaining / 1000L / 60L) + 1

                messageUser.value = StatesLogin(
                    "Muitas tentativas erradas. Tente novamente em $minutes minuto(s).", true
                )
                delay(1000L)
                timeRemaining = loginAttemptManager.remainingLockoutTime()

            }

            messageUser.value = StatesLogin(
                "Tente novamente", false
            )
            loginAttemptManager.resetAttempts()

        }
    }

    fun loginUserMain(identificadorLogin: String, userSecureLogin: String) {
        _loginStateLogin.value = AuthState.loadingAuth

        viewModelScope.launch {
            try {

                val typeAuthLogin = IdentificadorUtils.detectType(identificadorLogin)
                authRepositoryLogin.login(identificadorLogin, userSecureLogin, typeAuthLogin)
                _loginStateLogin.value = AuthState.sucessAuth

            } catch (e: Exception) {
                _loginStateLogin.value = AuthState.Error(e.message ?: "Credenciais inválidas")
            }
        }
    }


}