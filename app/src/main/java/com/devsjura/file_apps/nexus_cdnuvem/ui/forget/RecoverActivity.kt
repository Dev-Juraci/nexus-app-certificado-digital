package com.devsjura.file_apps.nexus_cdnuvem.ui.forget

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.transition.Visibility
import com.devsjura.file_apps.nexus_cdnuvem.R
import com.devsjura.file_apps.nexus_cdnuvem.animations.AnimaStart
import com.devsjura.file_apps.nexus_cdnuvem.databinding.ActivityRecoverBinding
import com.devsjura.file_apps.nexus_cdnuvem.ui.login.LoginActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.concurrent.thread
import kotlin.time.Duration.Companion.milliseconds

class RecoverActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityRecoverBinding.inflate(layoutInflater)
    }

    private val animaStartForget by lazy {
        AnimaStart()
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.recoverPasswordMain)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        animaStartForget.objectAnimaImgTxt(binding.containerAppName, -15F, 1250L)

        with(binding) {
            btnBackForget.setOnClickListener {
                startActivity(Intent(this@RecoverActivity, LoginActivity::class.java))
                finish()
            }

            btnSendCode.setOnClickListener {
                val userEmail = inputEmail.text.toString()

                val verifEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()


                if (userEmail.isNotBlank() && verifEmail) {
                    getSharedPreferences("meu_app_prefs", Context.MODE_PRIVATE).edit {
                        putString("email_of_user", userEmail)
                    }

                    containerEmailRecovery.visibility = View.GONE
                    containerVerificationCode.visibility = View.VISIBLE
                    umTeste()

                } else {
                    Snackbar.make(
                        binding.root,
                        "Por favor, insira um e-mail válido",
                        Snackbar.LENGTH_LONG
                    ).show()
                }


            }
        }

    }

    @SuppressLint("SetTextI18n")
    fun umTeste() {
        val sharedPreferencesTxt =
            getSharedPreferences("meu_app_prefs", Context.MODE_PRIVATE).getString(
                "email_of_user",
                ""
            )

        binding.txtVerificationInstruction.text =
            "Digite o código de 6 dígitos enviado para: $sharedPreferencesTxt"

    }
}