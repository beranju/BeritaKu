package com.nextgen.beritaku.auth.login

import androidx.lifecycle.ViewModel
import com.nextgen.beritaku.core.data.source.repository.AuthRepository
import com.nextgen.beritaku.core.domain.repository.IAuthRepository

class LoginViewModel(private val repository: IAuthRepository = AuthRepository()): ViewModel() {

}