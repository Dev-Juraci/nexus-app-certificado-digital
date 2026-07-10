package com.devsjura.file_apps.nexus_cdnuvem.others

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class LoginAttemptManager(context: Context) {

    private val prefs = context.getSharedPreferences("user_login", Context.MODE_PRIVATE)

    companion object {
        private const val MAX_BLOCKAGE = 5
        private const val TIME_BLOCKAGE_MS = 10 * 60 * 1000L

    }

    fun remainingLockoutTime(): Long {
        val blockedUntil = prefs.getLong("blockedUntil", 0L)
        val remainder = blockedUntil - System.currentTimeMillis()
        return if (remainder > 0) remainder else 0L
    }

    fun logFailedAttempt() {

        val recoverBtnVisible = prefs.getBoolean("make_Btn_Visible", false)
        val nOfAttempts = prefs.getInt("count_The_Attempts", 0) + 1
        prefs.edit { putInt("count_the_attempts", nOfAttempts) }

        if (nOfAttempts == 3) {
            !recoverBtnVisible
        } else if (nOfAttempts == 4) {
            !recoverBtnVisible
        } else if (nOfAttempts >= MAX_BLOCKAGE) {
            val blockedUntilOthers = System.currentTimeMillis() + TIME_BLOCKAGE_MS
            prefs.edit { putLong("blockedUntil", blockedUntilOthers) }
        }

    }

    fun resetAttempts() {
        prefs.edit {
            putLong("blockedUntil", 0L)
            putInt("count_The_Attempts", 0)
                .putBoolean("make_Btn_Visible", false)
        }
    }


}