package com.devsjura.file_apps.nexus_cdnuvem.viewmodel

import androidx.lifecycle.ViewModel
import com.devsjura.file_apps.nexus_cdnuvem.repository.AuthRepository
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class RegisterUsersViewModel(
    private val authFirebase: AuthRepository = AuthRepository(),
    private val fbFire: FirebaseFirestore = Firebase.firestore,
) : ViewModel() {


}