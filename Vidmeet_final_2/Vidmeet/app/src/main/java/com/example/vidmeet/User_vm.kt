package com.example.vidmeet

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class User_vm:ViewModel() {

    val auth = Firebase.auth

    private val _displayname = mutableStateOf("User")
    val displayname: String get() = _displayname.value
    fun updateDisplayName(name: String) {
            _displayname.value = name
    }
}
