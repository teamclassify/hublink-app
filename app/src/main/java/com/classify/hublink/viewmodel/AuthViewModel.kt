package com.classify.hublink.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.classify.hublink.HublinkApplication
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.classify.hublink.data.entities.UserProfile

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val userId: String) : AuthState()
    data class Error(val message: String) : AuthState()
}

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()
    private val _isLogged = MutableStateFlow(false)

    val currentUserProfile = MutableStateFlow<UserProfile?>(null)

    val isLogged: StateFlow<Boolean> = _isLogged

    val userEmail: String?
        get() = auth.currentUser?.email

    private val db = Firebase.firestore

    private val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
        val user = firebaseAuth.currentUser

        if (user != null) {
            _isLogged.value = true

            if (_authState.value !is AuthState.Success) {
                _authState.value = AuthState.Success(user.uid)
            }
        } else {
            _isLogged.value = false
            _authState.value = AuthState.Idle
            Log.d("AUTH", "SignOut successful and state updated.")
        }
    }

    init {
        _authState.value = AuthState.Loading
        auth.addAuthStateListener(authStateListener)
    }

    override fun onCleared() {
        super.onCleared()
        auth.removeAuthStateListener(authStateListener)
    }

    fun signIn(email: String, password: String) {
        _authState.value = AuthState.Loading

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    _authState.value = AuthState.Error(
                        task.exception?.message ?: "Error desconocido"
                    )
                }
            }
    }

    fun register(email: String, password: String) {
        _authState.value = AuthState.Loading

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Success(auth.currentUser?.uid ?: "")
                    Log.d(TAG, "createUserWithEmail:success")
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        HublinkApplication().baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }
    fun signOut() {
        auth.signOut()
    }

    /**
     * This method checks if the user profile exists in the
     * Firestore database.
     * result -> True if the user profile exists, false otherwise
     */
    fun checkUserProfile(onResult: (Boolean) -> Unit) {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            db.collection("users").document(uid).get() // <-- Use db here
                .addOnSuccessListener { document ->
                    onResult(document.exists())
                }
                .addOnFailureListener {
                    onResult(false)
                }
        }
    }

    /**
     * This method saves the user profile in the Firestore
     * database.
     */
    fun saveUserProfile(userProfile: UserProfile, onSuccess: () -> Unit) {
        val uid = auth.currentUser?.uid ?: return
        val profileToSave = userProfile.copy(id = uid, email = auth.currentUser?.email ?: "")

        db.collection("users").document(uid).set(profileToSave) // <-- And here
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { e ->
                // Handle error
            }
    }

    /**
     * This method fetches the user profile from Firestore
     * and updates the currentUserProfile StateFlow.
     */
    fun loadUserProfile() {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            db.collection("users").document(uid).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val profile = document.toObject(UserProfile::class.java)
                        currentUserProfile.value = profile
                        Log.d(TAG, "Profile loaded successfully: ${profile?.name}")
                    } else {
                        currentUserProfile.value = null
                        Log.w(TAG, "User profile document does not exist for UID: $uid")
                    }
                }
                .addOnFailureListener { e ->
                    currentUserProfile.value = null
                    Log.e(TAG, "Error fetching user profile", e)
                }
        } else {
            currentUserProfile.value = null
        }
    }
}