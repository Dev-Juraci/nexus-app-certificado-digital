package com.devsjura.file_apps.nexus_cdnuvem.ui.passwords

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.devsjura.file_apps.nexus_cdnuvem.R
import com.devsjura.file_apps.nexus_cdnuvem.databinding.ActivityPasswordBinding

class PasswordActivity : AppCompatActivity() {

    private lateinit var bindingPass: ActivityPasswordBinding

    private val sharedPreferencesPass by lazy {
        getSharedPreferences("secure_prefs", MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        bindingPass = ActivityPasswordBinding.inflate(layoutInflater)
        setContentView(bindingPass.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainPasswordSetup)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val names = sharedPreferencesPass.getString("app_encrypted_settings_names", "")
        val email = sharedPreferencesPass.getString("app_encrypted_settings_email", "")
        val cpf = sharedPreferencesPass.getString("app_encrypted_settings_cpf", "")
        val numb = sharedPreferencesPass.getString("app_encrypted_settings_number", "")

        bindingPass.txtTeste.text = "Nome: $names, Email: $email, CPF: $cpf, N° de Telefone: $numb"

    }
}