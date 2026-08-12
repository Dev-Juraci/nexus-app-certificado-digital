package com.devsjura.file_apps.nexus_cdnuvem.ui.passwords

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.devsjura.file_apps.nexus_cdnuvem.R
import com.devsjura.file_apps.nexus_cdnuvem.databinding.ActivityPasswordBinding
import com.devsjura.file_apps.nexus_cdnuvem.ui.home.MainActivity
import com.devsjura.file_apps.nexus_cdnuvem.viewmodel.AuthState
import com.devsjura.file_apps.nexus_cdnuvem.viewmodel.AuthViewModel
import com.google.android.material.snackbar.Snackbar

class PasswordActivity : AppCompatActivity() {

    private val viewModel: AuthViewModel by viewModels()
    private lateinit var bindingPass: ActivityPasswordBinding

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

        val sharedPreferencesPass =
            getSharedPreferences("secure_prefs", MODE_PRIVATE)

        val names =
            sharedPreferencesPass.getString("app_encrypted_settings_names", "").toString()
        val email =
            sharedPreferencesPass.getString("app_encrypted_settings_email", "").toString()
        val cpf = sharedPreferencesPass.getString("app_encrypted_settings_cpf", "").toString()
        val numb =
            sharedPreferencesPass.getString("app_encrypted_settings_number", "").toString()



        bindingPass.btnSubmit.setOnClickListener {

            val testPai = bindingPass.tilPassword
            val test = bindingPass.etPassword.text.toString()
            if (test.isBlank() && test.length <= 5) {
                testPai.error = "Informe uma senha válida com no mínimo 6 caracteres."
            }



            viewModel.registerUsers(names, email, cpf, numb, test)

        }

        observeState()


    }

    private fun observeState() {
        viewModel.stateRegistration.observe(this) { state ->

            when (state) {
                is AuthState.LoadingAuth -> {

                    val params = bindingPass.btnSubmit as ConstraintLayout.LayoutParams
                    params.topMargin = 130.toPx()
                    bindingPass.progressBarRegis.visibility = View.VISIBLE
                    bindingPass.txtAuthRegis.visibility = View.VISIBLE
                    bindingPass.btnSubmit.isEnabled = false
                }

                is AuthState.successAuth -> {
                    bindingPass.progressBarRegis.visibility = View.GONE
                    bindingPass.txtAuthRegis.visibility = View.GONE
                    startActivity(Intent(this@PasswordActivity, MainActivity::class.java))
                    finish()
                }

                is AuthState.Error -> {
                    bindingPass.progressBarRegis.visibility = View.GONE
                    bindingPass.txtAuthRegis.visibility = View.GONE
                    bindingPass.btnSubmit.isEnabled = false
                    Snackbar.make(bindingPass.root, state.msgErro, Snackbar.LENGTH_LONG).show()
                }


                is AuthState.Idle -> Unit

            }

        }
    }

    fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
}