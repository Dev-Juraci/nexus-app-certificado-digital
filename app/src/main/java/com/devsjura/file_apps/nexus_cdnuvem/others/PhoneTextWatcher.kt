package com.devsjura.file_apps.nexus_cdnuvem.others

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

abstract class PhoneTextWatcher(private val editText: EditText) : TextWatcher {

    private var isFormatting = false
    private var previousLength = 0

    override fun afterTextChanged(p0: Editable?) {
        previousLength = p0?.length ?: 0
    }

    override fun beforeTextChanged(
        p0: CharSequence?,
        p1: Int,
        p2: Int,
        p3: Int,
    ) {
        TODO("Not yet implemented")
    }

    override fun onTextChanged(
        p0: CharSequence?,
        p1: Int,
        p2: Int,
        p3: Int,
    ) {

        if (isFormatting || p0 == null) return

        isFormatting = true

        val digits = p0.toString().filter { it.isDigit() }.take(11)

        val formatted = formatPhone(digits)

        editText.setText(formatted)

        editText.setSelection(formatted.length)

        isFormatting = false


    }

    private fun formatPhone(digits: String): String {

        val sb = StringBuilder()

        digits.forEachIndexed { index, ch ->

            when (index) {
                0 -> sb.append("(")
                2 -> sb.append(") (")
                3 -> sb.append(") ")
                7 -> sb.append("-")
            }

            sb.append(ch)

        }
        return sb.toString()
    }
}