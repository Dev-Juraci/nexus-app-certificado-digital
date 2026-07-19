package com.devsjura.file_apps.nexus_cdnuvem.validation

class ValidatorInputs {

    fun isValidadorNames(receivesNames: String): String? {

        val validNameRegex =
            "^[A-Za-fA-Za-uÀ-ÿ]{2,}(?:\\s+(?:de|da|do|dos|das|e|[A-Za-fA-Za-uÀ-ÿ]{2,}))+$".toRegex()

        val namesIsValidador = receivesNames.trim().replace("\\s+".toRegex(), " ")

        return when {
            namesIsValidador.isBlank() -> "Preenche o campo de nomes"
            namesIsValidador.length < 5 -> "O nome completo precisa ter pelo menos 5 caracteres."
            namesIsValidador.length > 100 -> "O nome não pode ter mais de 100 caracteres."
            !namesIsValidador.matches(validNameRegex) -> "Digite um nome válido (apenas letras, mínimo de 2 letras por palavra)"
            else -> null
        }


    }

}