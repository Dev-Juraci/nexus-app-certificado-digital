package com.devsjura.file_apps.nexus_cdnuvem.ui.passwords

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.marginTop
import com.devsjura.file_apps.nexus_cdnuvem.R
import com.devsjura.file_apps.nexus_cdnuvem.databinding.ActivityPasswordBinding
import com.devsjura.file_apps.nexus_cdnuvem.ui.home.MainActivity
import com.devsjura.file_apps.nexus_cdnuvem.viewmodel.AuthState
import com.devsjura.file_apps.nexus_cdnuvem.viewmodel.AuthViewModel

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

        val names = sharedPreferencesPass.getString("app_encrypted_settings_names", "")
        val email = sharedPreferencesPass.getString("app_encrypted_settings_email", "")
        val cpf = sharedPreferencesPass.getString("app_encrypted_settings_cpf", "")
        val numb = sharedPreferencesPass.getString("app_encrypted_settings_number", "")

        observeState()


    }

    private fun observeState() {
        viewModel.loginState.observe(this) { state ->

            when (state) {
                is AuthState.LoadingAuth {

                    val params = bindingPass.btnSubmit as LinearLayout.LayoutParams
                    params.topMargin = 130.toPx()
                    bindingPass.progressBarRegis.visibility = View.VISIBLE
                    bindingPass.txtAuthRegis.visibility = View.VISIBLE
                    bindingPass.btnSubmit.isEnabled = false
                }

                is AuthState.successAuth {
                    bindingPass.progressBarRegis.visibility = View.GONE
                    bindingPass.txtAuthRegis.visibility = View.GONE
                    startActivity(Intent(this@PasswordActivity, MainActivity::class.java))
                    finish()
                }


                is AuthState.Idle -> Unit

            }

        }
    }

    fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
}