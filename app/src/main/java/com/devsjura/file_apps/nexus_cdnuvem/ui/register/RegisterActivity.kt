package com.devsjura.file_apps.nexus_cdnuvem.ui.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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

        binding.btnCreateAccount.setOnClickListener {
            val tstName = binding.etName.text.toString()
            val okok = ValidatorInputs().isValidatorNames(tstName)
            binding.etName.error = okok

            val tstCPF = binding.etCpf.text.toString()
           val okokCPF = ValidatorInputs().isValidatorCPF(tstCPF)

            if (okokCPF != null){
                binding.etCpf.error = okokCPF

            } else {
                binding.etCpf.error = null

            }


        }


    }
}