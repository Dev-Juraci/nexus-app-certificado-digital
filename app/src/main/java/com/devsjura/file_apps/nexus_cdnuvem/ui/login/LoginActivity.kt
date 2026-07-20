package com.devsjura.file_apps.nexus_cdnuvem.ui.login

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Patterns
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.devsjura.file_apps.nexus_cdnuvem.R
import com.devsjura.file_apps.nexus_cdnuvem.animations.AnimaStart
import com.devsjura.file_apps.nexus_cdnuvem.databinding.ActivityLoginBinding
import com.devsjura.file_apps.nexus_cdnuvem.others.LoginAttemptManager
import com.devsjura.file_apps.nexus_cdnuvem.ui.forget.RecoverActivity
import com.devsjura.file_apps.nexus_cdnuvem.ui.home.MainActivity
import com.devsjura.file_apps.nexus_cdnuvem.ui.register.RegisterActivity
import com.devsjura.file_apps.nexus_cdnuvem.viewmodel.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private val animaStart by lazy {
        AnimaStart()
    }
    private lateinit var binding: ActivityLoginBinding

    private val loginViewModel: LoginViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return LoginViewModel(LoginAttemptManager(this@LoginActivity)) as T
            }

        }
    }


    override fun onStart() {
        super.onStart()
        val userloggedIn = FirebaseAuth.getInstance().currentUser

        if (userloggedIn != null) {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        }

    }


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.loginMain)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        animaStart.objectAnimaImgTxt(binding.containerLogo, -20F, 1250L)

        loginViewModel.checkInitialLock()



        loginViewModel.loginSucess.observe(this) { sucess ->
            if (sucess) {
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            }
        }

        loginViewModel.messageUser.observe(this) { statesLogin ->
            if (statesLogin.viewLinear) {
                binding.txtLockoutMessage.text = statesLogin.msgToTheUser
                binding.layoutFormFields.visibility = View.GONE
                binding.containerLockoutView.visibility = View.VISIBLE
            } else {
                binding.containerLockoutView.visibility = View.GONE
                Snackbar.make(binding.root, statesLogin.msgToTheUser, Snackbar.LENGTH_LONG).show()
                binding.layoutFormFields.visibility = View.VISIBLE
            }

        }





        with(binding) {
            btnForgotPassword.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RecoverActivity::class.java))
            }

            txtCreateAccount.text = Html.fromHtml(
                getString(R.string.loginCreateAccount),
                Html.FROM_HTML_MODE_LEGACY
            )

            txtCreateAccount.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }

            btnLogin.setOnClickListener {
                val inputUserEmail = binding.inputUsername.text.toString()
                val passwInputUser = binding.inputPassword.text.toString()

                if (inputUserEmail.isBlank() || passwInputUser.isBlank()) {
                    Snackbar.make(
                        binding.root,
                        "Preencha o e-mail e a senha.",
                        Snackbar.LENGTH_LONG
                    )
                        .show()
                    return@setOnClickListener
                } else if (!Patterns.EMAIL_ADDRESS.matcher(inputUserEmail).matches()) {
                    Snackbar.make(
                        binding.root,
                        "Digite um endereço de e-mail válido.",
                        Snackbar.LENGTH_LONG
                    ).show()
                    return@setOnClickListener
                } else if (passwInputUser.length < 6) {
                    Snackbar.make(
                        binding.root,
                        "A senha deve ter no mínimo 6 dígitos.",
                        Snackbar.LENGTH_LONG
                    ).show()
                    return@setOnClickListener

                }

                checkingInformation(inputUserEmail, passwInputUser)


            }

        }

    }

    private fun checkingInformation(inEm: String, paIn: String) {
        loginViewModel.loginUserMain(inEm, paIn)

    }


}