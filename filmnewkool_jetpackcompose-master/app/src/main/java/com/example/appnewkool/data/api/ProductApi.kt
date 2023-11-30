package com.example.appnewkool.data.api

import com.example.appnewkool.data.modeljson.HomeProductResponse
import com.example.appnewkool.data.modeljson.ProductRemote
import com.example.appnewkool.data.modeljson.AddUpdateProductRespose
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductApi {
    @GET("/api/product")
    suspend fun getAllProduct(@HeaderMap headers: Map<String, String?>): Response<HomeProductResponse>

    @POST("/api/product")
    suspend fun createProduct(
        @HeaderMap headers: Map<String, String?>,
        @Body product: ProductRemote
    ): Response<AddUpdateProductRespose>


    @PUT("/api/product/{id}")
    suspend fun updateProduct(
        @Path("id") id: Int,
        @HeaderMap headers: Map<String, String?>,
        @Body product: ProductRemote
    ): Response<AddUpdateProductRespose>
}