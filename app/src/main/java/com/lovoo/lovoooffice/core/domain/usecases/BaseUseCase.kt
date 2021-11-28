package com.lovoo.lovoooffice.core.domain.usecases

import com.lovoo.lovoooffice.common.base.state.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.internal.ChannelFlow
import retrofit2.HttpException
import java.io.IOException

abstract class BaseUseCase<T> {

    open fun useCaseName(): String = "BaseUseCase"

    fun baseFlow(invoke: suspend (flowCollector: FlowCollector<Resource<T>>) -> Unit) : Flow<Resource<T>> = flow {
        try{
            invoke(this)
        }catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "HttpException in ${useCaseName()}"))
        }catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage ?: "IOException in ${useCaseName()}"))
        }
    }

}
