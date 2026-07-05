package com.devsjura.file_apps.nexus_cdnuvem.animations

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.LinearLayout

@SuppressLint("Recycle")

class AnimaStart {
    fun objectAnimaImgTxt(idObject: LinearLayout) {

        ObjectAnimator.ofFloat(
            idObject,
            "translationY",
            0f,
            -20f
        ).apply {
            duration = 1500
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE

            interpolator = AccelerateDecelerateInterpolator()
        }.start()

    }
}

