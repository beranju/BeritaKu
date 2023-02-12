package com.nextgen.beritaku.core.data.source.repository

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.nextgen.beritaku.core.data.source.Resource
import com.nextgen.beritaku.core.domain.repository.IAuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class AuthRepository( private val auth: FirebaseAuth = FirebaseAuth.getInstance()): IAuthRepository {
    override fun loginWithEmail(email: String, password: String): Flow<Resource<Unit>> {
        val dataFlow = MutableStateFlow<Resource<Unit>>(Resource.Loading())
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task->
                    if (task.isSuccessful){
                        dataFlow.value = Resource.Success(Unit)
                    }else{
                        dataFlow.value = Resource.Error(task.exception?.message.toString())
                    }
                }
        }catch (e: Exception){
            dataFlow.value = Resource.Error(e.message.toString())
        }
        return dataFlow
    }

    override fun registerWithEmail(
        email: String,
        password: String,
        name: String
    ): Flow<Resource<Unit>> {
        val dataFlow = MutableStateFlow<Resource<Unit>>(Resource.Loading())
        try {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        auth.currentUser?.let {
                            val profileUpdate = userProfileChangeRequest { displayName = name }
                            it.updateProfile(profileUpdate)
                        }
                        dataFlow.value = Resource.Success(Unit)
                    }else{
                        try {
                            task.exception
                        }catch (invalidEmail: FirebaseAuthInvalidCredentialsException){
                            dataFlow.value = Resource.Error("onComplete: Malformed email")
                        }catch (existsEmail: FirebaseAuthUserCollisionException){
                            dataFlow.value = Resource.Error("onComplete: exists email")
                        }catch (e: Exception){
                            dataFlow.value = Resource.Error("onComplete: ${e.message}")
                        }
                    }
                }
        }catch (e: Exception){
            dataFlow.value = Resource.Error(e.message.toString())
        }
        return dataFlow
    }

    override fun updateUserData(name: String, uri: Uri): Flow<Resource<Unit>> {
        val dataFlow = MutableStateFlow<Resource<Unit>>(Resource.Loading())
        try {
            val currentUser = getUser()
            currentUser?.let { user->
                val updateRequest = userProfileChangeRequest {
                    displayName = name
                    photoUri = uri
                }
                user.updateProfile(updateRequest)
                    .addOnSuccessListener {
                        dataFlow.value = Resource.Success(Unit)
                    }
                    .addOnFailureListener {
                        dataFlow.value = Resource.Error(it.message.toString())
                    }
            }
        }catch (e: Exception){
            dataFlow.value = Resource.Error(e.message.toString())
        }
        return dataFlow
    }

    override fun getUser(): FirebaseUser? = auth.currentUser

}