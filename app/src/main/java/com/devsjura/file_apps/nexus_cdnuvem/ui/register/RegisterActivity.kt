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
import com.devsjura.file_apps.nexus_cdnuvem.validation.ValidatorInputs

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


        etPhone.addTextChangedListener(
            object : TextWatcher {

                private var previousText = ""

                override fun afterTextChanged(p0: Editable?) {

                    if (formatando) return


                    val txtTyped = p0.toString()

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
                        ValidatorInputs().formatPhoneWhileTyping(numbersProvided)

                    if (formattedPhoneNumber != txtTyped) {
                        formatando = true
                        etPhone.setText(formattedPhoneNumber)
                        etPhone.setSelection(formattedPhoneNumber.length)
                        formatando = false
                    }


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