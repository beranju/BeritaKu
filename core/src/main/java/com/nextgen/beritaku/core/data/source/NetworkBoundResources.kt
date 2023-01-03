package com.nextgen.beritaku.core.data.source

import com.nextgen.beritaku.core.data.source.remote.network.ApiResponse
import com.nextgen.beritaku.core.utils.AppExecutors
import kotlinx.coroutines.flow.*

abstract class NetworkBoundResources<ResultType, RequestType>(private val appExecutors: AppExecutors) {

    private val result:Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading())
        val dbSource = loadFromDb().first()
        if (shouldFetch(dbSource)){
            emit(Resource.Loading())
            when(val api = createCall().first()){
                is ApiResponse.Empty ->{
                    emitAll(loadFromDb().map { Resource.Success(it) })
                }
                is ApiResponse.Success -> {
                    saveCallResult(api.data)
                    emitAll(loadFromDb().map { Resource.Success(it) })
                }
                is ApiResponse.Error ->{
                    onFetchFailed()
                    emit(Resource.Error<ResultType>(api.message))
                }
                else -> {}
            }
        }else{
            emitAll(loadFromDb().map { Resource.Success(it) })
        }
    }

    protected open fun onFetchFailed(){}

    protected abstract suspend fun saveCallResult(data: RequestType)

    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>

    protected abstract fun shouldFetch(dbSource: ResultType?): Boolean

    protected abstract fun loadFromDb(): Flow<ResultType>

    fun asFlow(): Flow<Resource<ResultType>> = result
}