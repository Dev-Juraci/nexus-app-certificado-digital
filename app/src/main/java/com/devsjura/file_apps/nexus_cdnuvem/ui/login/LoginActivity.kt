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
import com.devsjura.file_apps.nexus_cdnuvem.others.AuthState
import com.devsjura.file_apps.nexus_cdnuvem.others.LoginAttemptManager
import com.devsjura.file_apps.nexus_cdnuvem.repository.AuthRepository
import com.devsjura.file_apps.nexus_cdnuvem.ui.forget.RecoverActivity
import com.devsjura.file_apps.nexus_cdnuvem.ui.home.MainActivity
import com.devsjura.file_apps.nexus_cdnuvem.ui.register.RegisterActivity
import com.devsjura.file_apps.nexus_cdnuvem.viewmodel.AuthViewModel
import com.devsjura.file_apps.nexus_cdnuvem.viewmodel.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private val viewModelLogins: LoginViewModel by viewModels()
    private val animaStart by lazy {
        AnimaStart()
    }
    private lateinit var binding: ActivityLoginBinding

    private val viewModelLogin: LoginViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return LoginViewModel(
                    AuthRepository(),
                    LoginAttemptManager(this@LoginActivity)
                ) as T
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

        viewModelLogin.checkInitialLock()
        observeStateLogin()

//
//
//
//        loginViewModel.loginSucess.observe(this) { sucess ->
//            if (sucess) {
//                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
//                finish()
//            }
//        }
//
//        loginViewModel.messageUser.observe(this) { statesLogin ->
//            if (statesLogin.viewLinear) {
//                binding.txtLockoutMessage.text = statesLogin.msgToTheUser
//                binding.layoutFormFields.visibility = View.GONE
//                binding.containerLockoutView.visibility = View.VISIBLE
//            } else {
//                binding.containerLockoutView.visibility = View.GONE
//                Snackbar.make(binding.root, statesLogin.msgToTheUser, Snackbar.LENGTH_LONG).show()
//                binding.layoutFormFields.visibility = View.VISIBLE
//            }
//
//        }


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

//                if (inputUserEmail.isBlank() || passwInputUser.isBlank()) {
//                    Snackbar.make(
//                        binding.root,
//                        getString(R.string.preencha_o_e_mail_e_a_senha),
//                        Snackbar.LENGTH_LONG
//                    )
//                        .show()
//                    return@setOnClickListener
//                } else if (!Patterns.EMAIL_ADDRESS.matcher(inputUserEmail).matches()) {
//                    Snackbar.make(
//                        binding.root,
//                        getString(R.string.emails_infor),
//                        Snackbar.LENGTH_LONG
//                    ).show()
//                    return@setOnClickListener
//                } else if (passwInputUser.length < 6) {
//                    Snackbar.make(
//                        binding.root,
//                        "A senha deve ter no mínimo 6 dígitos.",
//                        Snackbar.LENGTH_LONG
//                    ).show()
//                    return@setOnClickListener
//
//                }

                viewModelLogin.loginUserMain(inputUserEmail, passwInputUser)

            }

        }



    }

    fun observeStateLogin() {
        viewModelLogin.loginStateLogin.observe(this) { stateLogin ->

            when (stateLogin) {
                is AuthState.loadingAuth -> {
                    binding.progressBarLogin.visibility = View.VISIBLE
                    binding.txtAuthLogin.visibility = View.VISIBLE
                    binding.btnLogin.isEnabled = false

                }

                is AuthState.sucessAuth -> {
                    binding.progressBarLogin.visibility = View.GONE
                    binding.txtAuthLogin.visibility = View.GONE
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                }

                is AuthState.Error -> {
                    binding.progressBarLogin.visibility = View.GONE
                    binding.txtAuthLogin.visibility = View.GONE
                    binding.btnLogin.isEnabled = true
                    Snackbar.make(binding.root, stateLogin.msgError, Snackbar.LENGTH_LONG).show()
                }

                is AuthState.Idle -> Unit

            }

        }
    }


}