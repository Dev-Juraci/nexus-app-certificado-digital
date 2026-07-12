package com.devsjura.file_apps.nexus_cdnuvem.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devsjura.file_apps.nexus_cdnuvem.others.LoginAttemptManager
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import kotlin.getValue

class LoginViewModel(private val loginAttemptManager: LoginAttemptManager) : ViewModel() {
    private val fbAuth by lazy {
        FirebaseAuth.getInstance()
    }

    val loginSucess = MutableLiveData<Boolean>()

    val messageUser = MutableLiveData<String>()

    fun loginUserMain(emailLogin: String, passLogin: String) {

        val remaTime = loginAttemptManager.remainingLockoutTime()

        if (remaTime > 0) {
            val minutes = (remaTime / 1000L / 60L) + 1
            messageUser.value =
                "Muitas tentativas erradas. Tente novamente em $minutes minuto(s)."
            return
        }

        fbAuth.signInWithEmailAndPassword(emailLogin, passLogin).addOnSuccessListener {
            loginAttemptManager.resetAttempts()
            loginSucess.value = true
        }

            .addOnFailureListener {
                val tst = loginAttemptManager.logFailedAttempt()
                messageUser.value = "$tst."
            }

    }


}