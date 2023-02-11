package com.nextgen.beritaku.core.domain.repository

import com.nextgen.beritaku.core.data.source.Resource
import kotlinx.coroutines.flow.Flow

interface IAuthRepository {
    fun loginWithEmail(email: String, password: String): Flow<Resource<Unit>>
    fun registerWithEmail(email: String, password: String, name: String): Flow<Resource<Unit>>
}