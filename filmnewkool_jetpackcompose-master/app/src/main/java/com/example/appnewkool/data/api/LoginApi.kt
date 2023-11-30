package com.example.appnewkool.data.api


import com.example.appnewkool.data.model.Account
import com.example.appnewkool.data.modeljson.SignInResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("/api/login")
    suspend fun signIn(@Body user: Account): Response<SignInResponse>
}