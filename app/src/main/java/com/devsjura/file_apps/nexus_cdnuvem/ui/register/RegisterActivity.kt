package com.devsjura.file_apps.nexus_cdnuvem.ui.register

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged
import com.devsjura.file_apps.nexus_cdnuvem.R
import com.devsjura.file_apps.nexus_cdnuvem.animations.AnimaStart
import com.devsjura.file_apps.nexus_cdnuvem.databinding.ActivityRegisterBinding
import com.devsjura.file_apps.nexus_cdnuvem.ui.login.LoginActivity
import com.devsjura.file_apps.nexus_cdnuvem.validation.ValidatorInputs
import com.google.android.material.snackbar.Snackbar

class RegisterActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }

    private val animaStartRegister by lazy {
        AnimaStart()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.tvBack.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            finish()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainRegister)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        animaStartRegister.objectAnimaImgTxt(binding.containerRegisterLogo, -15F, 1250L)

        val tilPhone = binding.tilPhone
        val etPhone = binding.etPhone

        var formatando = false

        etPhone.doAfterTextChanged { txtTyped ->

            if (formatando) return@doAfterTextChanged

            val numbersProvided = txtTyped.toString().filter {
                it.isDigit()
            }.take(11)

            if (numbersProvided.isBlank()) {
                tilPhone.error = "Informe seu número de telefone."
            } else if (numbersProvided.length != 11) {
                tilPhone.error = "Digite um telefone válido com DDD."
            } else {
                tilPhone.error = null
            }

            val formattedPhoneNumber = ValidatorInputs().formatPhoneWhileTyping(numbersProvided)

            if (formattedPhoneNumber != txtTyped.toString()) {
                formatando = true
                etPhone.setText(formattedPhoneNumber)
                etPhone.setSelection(formattedPhoneNumber.length)
                formatando = false
            }
        }

        binding.btnCreateAccount.setOnClickListener {
            val tstName = binding.etName.text.toString()
            val okok = ValidatorInputs().isValidatorNames(tstName)
            binding.etName.error = okok

            val tstCPF = binding.etCpf.text.toString()
            val okokCPF = ValidatorInputs().isValidatorCPF(tstCPF)

            if (okokCPF != null) {
                binding.etCpf.error = okokCPF

            } else {
                binding.etCpf.error = null

            }

            val tstEmail = binding.etEmail.text.toString()

            val okokEmail = ValidatorInputs().isValidatorEmail(tstEmail)
            if (okokEmail != null) {
                binding.etEmail.error = okokEmail
            } else {
                binding.etEmail.error = null
            }


        }


    }
}