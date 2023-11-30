package com.example.appnewkool.data.base

import android.accounts.NetworkErrorException
import com.example.appnewkool.data.base.network.BaseNetworkException

abstract class BaseService {

    protected fun parseError(
        responseMessage: String?,
        responseCode: Int,
        errorBody: String?
    ): BaseNetworkException {

        val baseNetworkException =  BaseNetworkException(responseMessage,responseCode)
        errorBody?.let{
            baseNetworkException.parseFromString(it)
        }

        return baseNetworkException
    }

    protected fun parseNetworkErrorException(throwable: Throwable): NetworkErrorException {
        return NetworkErrorException(throwable.message)
    }

}