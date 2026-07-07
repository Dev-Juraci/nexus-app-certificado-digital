package com.devsjura.file_apps.nexus_cdnuvem.ui.forget

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.devsjura.file_apps.nexus_cdnuvem.R
import com.devsjura.file_apps.nexus_cdnuvem.animations.AnimaStart
import com.devsjura.file_apps.nexus_cdnuvem.databinding.ActivityRecoverBinding

class RecoverActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityRecoverBinding.inflate(layoutInflater)
    }

    private val animaStartForget by lazy {
        AnimaStart()
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.recoverPasswordMain)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        with(binding){

        }

    }
}