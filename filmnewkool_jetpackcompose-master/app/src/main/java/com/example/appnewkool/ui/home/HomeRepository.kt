package com.example.appnewkool.ui.home

import android.util.Log
import com.example.appnewkool.data.base.network.NetworkResult
import com.example.appnewkool.data.database.entities.toListProduct
import com.example.appnewkool.data.database.entities.toListProductEntity
import com.example.appnewkool.data.model.Product
import com.example.appnewkool.data.modeljson.toListProduct
import com.example.appnewkool.data.services.LocalService
import com.example.appnewkool.data.services.ProductRemoteService
import com.example.appnewkool.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.log

class HomeRepository @Inject constructor(
    private val localService: LocalService,
    private val productRemoteService: ProductRemoteService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    fun getListProduct() = localService.getAllProduct().map {
        if (it.isNotEmpty()) {
            it.toListProduct()
        } else {
            getProductAndSaveFromRemote()
        }
    }.flowOn(dispatcher)

    suspend fun getToken() = withContext(dispatcher) { productRemoteService.getSharePreference() }

    suspend fun getProductAndSaveFromRemote(): List<Product> {
        var newProductList = listOf<Product>()
        when (val result = productRemoteService.getListProductResponse()) {
            is NetworkResult.Success -> newProductList = result.data.data.toListProduct()
            is NetworkResult.Error -> throw result.exception
        }
        if (newProductList.isNotEmpty()) {
            localService.deleteAllProduct()
            localService.saveListProduct(newProductList.toListProductEntity())
        }
        return newProductList
    }

    fun searchData(query: String, event: EventGetData) =
        when (event) {
            is EventGetData.Search -> localService.searchProducts(query)
                .map { it.toListProduct() }.flowOn(dispatcher)
            is EventGetData.Sort -> localService.sortProducts(query)
                .map { it.toListProduct() }.flowOn(dispatcher)
        }


}