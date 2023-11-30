package com.example.appnewkool.data.model

import android.util.Patterns




class Account(val email: String, val password: String) {
    fun isEmailValid(): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }


    fun isPasswordLengthGreaterThan5(): Boolean {
        return password.length > 5
    }
}