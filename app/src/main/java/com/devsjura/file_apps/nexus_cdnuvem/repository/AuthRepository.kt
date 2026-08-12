package com.devsjura.file_apps.nexus_cdnuvem.repository

import com.devsjura.file_apps.nexus_cdnuvem.others.TipoIdentificador
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await


data class UsersInfo(
    val uid: String,
    val fullNames: String,
    val cpfUser: String,
    val emailUser: String,
    val numberUser: String,
)

class AuthRepository(
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val fbFire: FirebaseFirestore = Firebase.firestore,

    ) {


    suspend fun signUpUsers(
        nameAthRepo: String,
        cpfAthRepo: String,
        emailAthRepo: String,
        passAthRepo: String,
        numberAthRepo: String,
    ) {

        val result = passAthRepo.let {
            firebaseAuth.createUserWithEmailAndPassword(emailAthRepo, it).await()
        }

        val uuid = result.user?.uid ?: throw Exception("Falha ao criar usuário")

        val userAthRepo = UsersInfo(uuid, nameAthRepo, cpfAthRepo, emailAthRepo, nameAthRepo)

        fbFire.collection("usersApp").document(uuid).set(userAthRepo).await()

        if (cpfAthRepo != null) {
            fbFire.collection("indice_login").document(cpfAthRepo)
                .set(mapOf("email" to emailAthRepo))
                .await()
        }

        fbFire.collection("indice_login").document()
            .set(mapOf("email" to emailAthRepo)).await()
    }

    private suspend fun resolveEmail(identifierRE: String, tipo: TipoIdentificador): String {
        if (tipo == TipoIdentificador.EMAIL) return identifierRE

        val doc = fbFire.collection("indice_login").document(identifierRE).get().await()

        return doc.getString("email") ?: throw Exception("Usuário não encontrado")

    }

    suspend fun login(identifierLogin: String, secureLogin: String, type: TipoIdentificador) {
        val email = resolveEmail(identifierLogin, type)
        firebaseAuth.signInWithEmailAndPassword(email, secureLogin)
    }
}