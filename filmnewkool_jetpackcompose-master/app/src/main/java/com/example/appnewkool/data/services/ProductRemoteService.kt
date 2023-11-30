package com.example.appnewkool.data.services

import com.example.appnewkool.common.AppSharePreference
import com.example.appnewkool.data.api.ProductApi
import com.example.appnewkool.data.base.BaseRemoteService
import com.example.appnewkool.data.base.network.NetworkResult
import com.example.appnewkool.data.model.Product
import com.example.appnewkool.data.modeljson.HomeProductResponse
import com.example.appnewkool.data.modeljson.ProductRemote
import com.example.appnewkool.data.modeljson.AddUpdateProductRespose
import com.example.appnewkool.data.modeljson.toProductRemote
import javax.inject.Inject

class ProductRemoteService @Inject constructor(private val productApi: ProductApi) :
    BaseRemoteService() {
    @Inject
    lateinit var appSharePreference: AppSharePreference
    suspend fun getSharePreference(): String? {
        return appSharePreference.getSharedPreferences()?.getString("token", "")
    }

    suspend fun getListProductResponse(): NetworkResult<HomeProductResponse> {
        val getToken = appSharePreference.getSharedPreferences()?.getString("token", "")
        val header = mapOf("token" to getToken)

        return callApi { productApi.getAllProduct(header) }
    }

    suspend fun createNewProductResponse(product: Product): NetworkResult<AddUpdateProductRespose> {
        val getToken = appSharePreference.getSharedPreferences()?.getString("token", "")
        val header = mapOf("token" to getToken)
        return callApi { productApi.createProduct(header, product.toProductRemote()) }
    }

    suspend fun updateProductRemote(id:Int,product: ProductRemote): NetworkResult<AddUpdateProductRespose> {
        val getToken = appSharePreference.getSharedPreferences()?.getString("token", "")
        val header = mapOf("token" to getToken)
        return callApi { productApi.updateProduct(id,header,product) }
    }
}