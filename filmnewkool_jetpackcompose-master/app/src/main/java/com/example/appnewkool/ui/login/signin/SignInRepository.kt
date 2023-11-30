package com.example.appnewkool.ui.login.signin

import android.util.Log
import com.example.appnewkool.common.AppSharePreference
import com.example.appnewkool.common.Utils
import com.example.appnewkool.data.base.network.NetworkResult
import com.example.appnewkool.data.model.Account
import com.example.appnewkool.data.modeljson.SignInResponse
import com.example.appnewkool.data.services.SignInService
import com.example.appnewkool.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignInRepository @Inject constructor(
    private val signInService: SignInService,
    @IoDispatcher val dispatcher: CoroutineDispatcher,
) {
    @Inject
    lateinit var appSharePreference: AppSharePreference
    suspend fun signIn(userName: Account) = withContext(dispatcher) {
        when (val result = signInService.signInAccount(userName)) {
            is NetworkResult.Success -> {
                appSharePreference.getEditor()?.putString("token", result.data.token.token)?.apply()
                Log.e("TAG", "signIn: " + "success")
                result.data
            }
            is NetworkResult.Error -> {
                Log.e("TAG", "signIn: " + "fail")
                throw result.exception
            }
        }
    }

}