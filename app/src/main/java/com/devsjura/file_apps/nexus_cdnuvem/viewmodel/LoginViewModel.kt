package com.devsjura.file_apps.nexus_cdnuvem.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.annotation.LongDef
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devsjura.file_apps.nexus_cdnuvem.model.StatesLogin
import com.devsjura.file_apps.nexus_cdnuvem.others.LoginAttemptManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlin.getValue
import kotlin.math.log
import kotlin.time.Duration.Companion.milliseconds

class LoginViewModel(private val loginAttemptManager: LoginAttemptManager) : ViewModel() {
    private val fbAuth by lazy {
        FirebaseAuth.getInstance()
    }
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


                delay(1000L.milliseconds)
                timeRemaining = loginAttemptManager.remainingLockoutTime()

            }

            messageUser.value = StatesLogin(
                "", false
            )

        }
    }


    fun loginUserMain(emailLogin: String, passLogin: String) {


        fbAuth.signInWithEmailAndPassword(emailLogin, passLogin).addOnSuccessListener {

            loginAttemptManager.resetAttempts()
            loginSucess.value = true
        }

            .addOnFailureListener {
                loginAttemptManager.logFailedAttempt()
                if (loginAttemptManager.numberOfAttempts() <= 4) {
                    messageUser.value = StatesLogin("Email ou Senha inválido.", true)
                } else {
                    messageUser.value = StatesLogin("", false)
                    checkInitialLock()
                    return@addOnFailureListener
                }

            }


    }


}