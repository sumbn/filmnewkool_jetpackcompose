package com.example.appnewkool.ui.product.detail

import android.util.Log
import com.example.appnewkool.data.database.entities.toProduct
import com.example.appnewkool.data.model.Product
import com.example.appnewkool.data.services.LocalService
import com.example.appnewkool.data.services.ProductRemoteService
import com.example.appnewkool.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DetailProductRepository @Inject constructor(
    private val localService: LocalService,
    private val productRemoteService: ProductRemoteService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend fun getProduct(id: Int): Product = withContext(dispatcher) {
        localService.getProduct(id).toProduct()
    }

    suspend fun getToken() = withContext(dispatcher) {
        productRemoteService.getSharePreference()
    }
}