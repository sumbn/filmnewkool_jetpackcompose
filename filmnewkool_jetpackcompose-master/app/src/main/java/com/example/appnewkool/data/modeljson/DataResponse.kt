package com.example.appnewkool.data.modeljson

//SignIn
class SignInResponse(val code: String, val token: Token)
class Token(val token: String, val refresh_token: String, val token_expired: String)

//Home
class HomeProductResponse(val code: String, val data: List<ProductRemote>)


//Product
class AddUpdateProductRespose(val code: String, val message: String?, val data: ProductRemote)