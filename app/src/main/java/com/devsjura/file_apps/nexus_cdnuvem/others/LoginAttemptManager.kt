package com.devsjura.file_apps.nexus_cdnuvem.others

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import kotlin.time.Duration.Companion.seconds

class LoginAttemptManager(context: Context) {

    private val prefs = context.getSharedPreferences("user_login", Context.MODE_PRIVATE)

    companion object {
        private const val MAX_BLOCKAGE = 5
        private const val TIME_BLOCKAGE_MS = 1 * 60 * 1000L

    }

    fun remainingLockoutTime(): Long {
        val blockedUntil = prefs.getLong("blockedUntilShared", 0L)
        val remainder = blockedUntil - System.currentTimeMillis()
        return if (remainder > 0) remainder else 0L
    }

    fun numberOfAttempts(): Int {
        return prefs.getInt("count_The_Attempts", 0)
    }

    fun logFailedAttempt() {
        val valueAwaiting = numberOfAttempts() + 1
        prefs.edit { putInt("count_The_Attempts", valueAwaiting) }

        if (valueAwaiting >= MAX_BLOCKAGE) {
            val blockedUntil = System.currentTimeMillis() + TIME_BLOCKAGE_MS
            prefs.edit {
                putLong("blockedUntilShared", blockedUntil)
            }
        }


    }

    fun resetAttempts() {
        prefs.edit {
            putLong("blockedUntilShared", 0L)
            putInt("count_The_Attempts", 0)
                .putBoolean("make_Btn_Visible", false)
        }
    }


}