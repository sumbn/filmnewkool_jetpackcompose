package com.example.appnewkool.ui.product.edit

import com.example.appnewkool.data.base.network.NetworkResult
import com.example.appnewkool.data.database.entities.toProduct
import com.example.appnewkool.data.database.entities.toProductEntity
import com.example.appnewkool.data.model.Product
import com.example.appnewkool.data.modeljson.toProduct
import com.example.appnewkool.data.modeljson.toProductRemote
import com.example.appnewkool.data.services.LocalService
import com.example.appnewkool.data.services.ProductRemoteService
import com.example.appnewkool.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateProductRepository @Inject constructor(
    private val localService: LocalService,
    private val productRemoteService: ProductRemoteService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend fun updateProductRemote(id: Int, product: Product) = withContext(dispatcher) {
        when (val result = productRemoteService.updateProductRemote(id, product.toProductRemote())) {
            is NetworkResult.Success -> {updateProductLocal(result.data.data.toProduct())
            result.data}
            is NetworkResult.Error -> throw result.exception
        }
    }

    private suspend fun updateProductLocal(product: Product) = withContext(dispatcher) {
        localService.insertProduct(product.toProductEntity())
    }
    suspend fun getProduct(id: Int): Product = withContext(dispatcher) {
        localService.getProduct(id).toProduct()
    }
}