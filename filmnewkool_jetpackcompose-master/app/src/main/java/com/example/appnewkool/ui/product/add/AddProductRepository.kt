package com.example.appnewkool.ui.product.add

import com.example.appnewkool.data.base.network.NetworkResult
import com.example.appnewkool.data.database.entities.toProductEntity
import com.example.appnewkool.data.model.Product
import com.example.appnewkool.data.modeljson.toProduct
import com.example.appnewkool.data.services.LocalService
import com.example.appnewkool.data.services.ProductRemoteService
import com.example.appnewkool.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddProductRepository @Inject constructor(
    private val localService: LocalService,
    private val productRemoteService: ProductRemoteService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    suspend fun createProduct(product: Product) = withContext(dispatcher) {
        when (val result = productRemoteService.createNewProductResponse(product)) {
            is NetworkResult.Success -> {
                createProductLocal(result.data.data.toProduct())
                result.data
            }
            is NetworkResult.Error -> throw result.exception
        }
    }

    private suspend fun createProductLocal(product: Product) = withContext(dispatcher) {
        localService.insertProduct(product.toProductEntity())
    }
}