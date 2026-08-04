package com.devsjura.file_apps.nexus_cdnuvem.ui.register

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.devsjura.file_apps.nexus_cdnuvem.R
import com.devsjura.file_apps.nexus_cdnuvem.animations.AnimaStart
import com.devsjura.file_apps.nexus_cdnuvem.databinding.ActivityRegisterBinding
import com.devsjura.file_apps.nexus_cdnuvem.ui.login.LoginActivity
import com.devsjura.file_apps.nexus_cdnuvem.ui.passwords.PasswordActivity
import com.devsjura.file_apps.nexus_cdnuvem.validation.ValidatorInputs
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlin.getValue

class RegisterActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }

    private val animaStartRegister by lazy {
        AnimaStart()
    }

    private val tilPhone by lazy {
        binding.tilPhone
    }

    private val etPhone by lazy {
        binding.etPhone
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


        etPhone.addTextChangedListener(
            object : TextWatcher {

                private var previousText = ""

                override fun afterTextChanged(p0: Editable?) {

                    val txtTyped = p0.toString()
                    formatAndValidatePhone(previousText, txtTyped, tilPhone, etPhone)

                }


                override fun beforeTextChanged(
                    p0: CharSequence?,
                    p1: Int,
                    p2: Int,
                    p3: Int,
                ) {
                    previousText = p0.toString()
                }

                override fun onTextChanged(
                    p0: CharSequence?,
                    p1: Int,
                    p2: Int,
                    p3: Int,
                ) {
                    null
                }
            })

        binding.btnCreateAccount.setOnClickListener {
            val storingName = binding.etName.text.toString()
            val storingCPF = binding.etCpf.text.toString()
            val storingEmail = binding.etEmail.text.toString()
            val storingNumber = binding.etPhone.text.toString().filter {
                it.isDigit()
            }.take(11)
            validateInputs(storingName, storingCPF, storingEmail, storingNumber)

        }


    }


    private fun formatAndValidatePhone(
        previousText: String,
        txtTyped: String,
        tilPhone: TextInputLayout,
        etPhone: TextInputEditText,
    ) {

        var formatting = false


        if (formatting) return


        val isDeleting = txtTyped.length < previousText.length


        var numbersProvided = txtTyped.filter {
            it.isDigit()
        }.take(11)

        if (isDeleting) {
            val previousDigits = previousText.filter {
                it.isDigit()
            }.take(11)

            if (numbersProvided.length == previousDigits.length && numbersProvided.isNotEmpty()) {
                numbersProvided = numbersProvided.dropLast(1)
            }

        }
        if (numbersProvided.isBlank()) {
            tilPhone.error = "Informe seu número de telefone."
        } else if (numbersProvided.length != 11) {
            tilPhone.error = "Digite um telefone válido com DDD."
        } else {
            tilPhone.error = null
        }

        val formattedPhoneNumber =
            ValidatorInputs.formatPhoneWhileTyping(numbersProvided)

        if (formattedPhoneNumber != txtTyped) {
            formatting = true
            etPhone.setText(formattedPhoneNumber)
            etPhone.setSelection(formattedPhoneNumber.length)
            formatting = false
        }

    }


    private fun validateInputs(
        storName: String,
        storCPF: String,
        storEmail: String,
        storNumber: String,
    ) {

        val isCPFUser = ValidatorInputs.isValidatorCPF(storCPF)
        val isEmailUser = ValidatorInputs.isValidatorEmail(storEmail)
        val isNumberUser = ValidatorInputs.formatPhoneWhileTyping(storNumber).filter {
            it.isDigit()
        }.take(11)


        val isNameUser = ValidatorInputs.isValidatorNames(storName)
        binding.etName.error = isNameUser


        if (isEmailUser == null && isNameUser == null && isCPFUser == null) {
            binding.etEmail.error = null
            binding.etCpf.error = null
            binding.tilPhone.error = null
            startActivity(Intent(this@RegisterActivity, PasswordActivity::class.java))

        } else {
            binding.etCpf.error = isCPFUser
            binding.etEmail.error = isEmailUser
            if (isNumberUser.length != 11) {
                binding.tilPhone.error = "Número de telefone inválido."
            }
        }

    }
}