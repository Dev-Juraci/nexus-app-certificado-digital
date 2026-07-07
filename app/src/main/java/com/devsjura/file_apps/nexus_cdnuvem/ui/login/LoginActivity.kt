package com.devsjura.file_apps.nexus_cdnuvem.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.Html
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.devsjura.file_apps.nexus_cdnuvem.R
import com.devsjura.file_apps.nexus_cdnuvem.animations.AnimaStart
import com.devsjura.file_apps.nexus_cdnuvem.databinding.ActivityLoginBinding
import com.devsjura.file_apps.nexus_cdnuvem.ui.forget.RecoverActivity
import com.devsjura.file_apps.nexus_cdnuvem.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private val animaStart by lazy {
        AnimaStart()
    }
    private lateinit var binding: ActivityLoginBinding

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
        }

    }
}