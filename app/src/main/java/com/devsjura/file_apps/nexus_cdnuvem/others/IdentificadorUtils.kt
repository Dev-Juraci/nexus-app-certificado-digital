package com.devsjura.file_apps.nexus_cdnuvem.others

object IdentificadorUtils {

    fun detectType(identificador: String): TipoIdentificador {
        return when {
            identificador.contains("@") -> TipoIdentificador.EMAIL
            identificador.replace(Regex("[^0-9]"), "").length == 11 -> TipoIdentificador.CPF
            else -> TipoIdentificador.TELEFONE
        }
    }

}