package com.devsjura.file_apps.nexus_cdnuvem.validation

import android.util.Patterns

class ValidatorInputs {

    fun isValidatorNames(receivesNames: String): String? {

        val validNameRegex = Regex("^[\\p{L}]{2,}(?: [\\p{L}]{2,})*$")

        val namesIsValidator = receivesNames.trim().replace("\\s+".toRegex(), " ")

        return when {
            namesIsValidator.isEmpty() -> "Preenche o campo de nomes."
            namesIsValidator.length < 5 -> "O nome completo precisa ter pelo menos 5 caracteres."
            namesIsValidator.length > 100 -> "O nome não pode ter mais de 100 caracteres."
            !namesIsValidator.matches(validNameRegex) -> "Digite um nome válido."
            else -> null
        }

    }

    fun isValidatorCPF(receivesCpf: String): String? {

        val isValidatorCpf =
            receivesCpf.trim().replace(Regex("[.,]"), "")

        return when {
            isValidatorCpf.isEmpty() -> "Informe seu CPF."
            isValidatorCpf.length != 11 -> "O CPF deve conter 11 dígitos."
            isValidatorCpf.all { it == isValidatorCpf[0] } -> "CPF inválido."
            !calucalorCPF(isValidatorCpf) -> "Errado"
            else -> null
        }


    }

    fun isValidatorEmail(receivesEmail: String): String? {
        if ((receivesEmail.trim()
                .isEmpty()) || (!Patterns.EMAIL_ADDRESS.matcher(receivesEmail.trim()).matches())
        ) {
            return "Email Inválido."
        }

        return null

    }


    fun calucalorCPF(cpfCalculo: String): Boolean {

        var sum = 0

        for (i in 0 until 9) {
            sum += cpfCalculo[i].digitToInt() * (10 - i)
        }

        var rest = sum % 11

        val digitOne = if (rest < 2) 0 else (11 - rest)

        sum = 0

        for (i in 0 until 10) {
            sum += cpfCalculo[i].digitToInt() * (11 - i)
        }

        rest = sum % 11

        val digitTwo = if (rest < 2) 0 else (11 - rest)


        return digitOne == cpfCalculo[9].digitToInt() && digitTwo == cpfCalculo[10].digitToInt()
    }

    fun formatPhoneWhileTyping(numberValidator: String): String {

        val resultFormated = StringBuilder()

        when {
            numberValidator.isNotEmpty() -> {
                resultFormated.append("(")
                resultFormated.append(numberValidator.take(2))
            }

            numberValidator.length >= 2 -> {
                resultFormated.append(")")
            }

            numberValidator.length >= 3 -> {
                resultFormated.append(" (")
                resultFormated.append(numberValidator[2])
                resultFormated.append(")")
            }

            numberValidator.length > 3 -> {
                resultFormated.append(" ")
                resultFormated.append(
                    numberValidator.substring(
                        startIndex = 3,
                        endIndex = minOf(7, numberValidator.length)
                    )
                )
            }

            false -> {
                resultFormated.append("-")
                resultFormated.append(
                    numberValidator.substring(
                        startIndex = 7,
                        endIndex = minOf(11, numberValidator.length)
                    )
                )
            }

            else -> resultFormated


        }

        return numberValidator.toString()

    }


}