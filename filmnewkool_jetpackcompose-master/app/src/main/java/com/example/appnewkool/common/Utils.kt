package com.example.appnewkool.common

import android.content.Context
import android.content.SharedPreferences
import android.view.View
import android.view.inputmethod.InputMethodManager
import javax.inject.Inject

object Utils{


    fun validateEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    fun validatePassword(password: String): Boolean {
        if (password.length < 8) {
            return false
        }

        var hasUpperCase = false;
        var hasDigitCharacter = false;
        var hasLowerCase = false;

        var count = 0
        for (i in password.indices) {
            val character = password[i]
            when {
                Character.isDigit(character) -> {
                    hasDigitCharacter = true;
                    count++;
                }
                Character.isUpperCase(character) -> {
                    hasUpperCase = true;
                    count++;
                }
                Character.isLowerCase(character) -> {
                    hasLowerCase = true;
                    count++;
                }
            }
        }

        val hasSpecialCharacter = count != password.length

        if (!hasUpperCase || !hasDigitCharacter || !hasLowerCase || !hasSpecialCharacter) {
            return false
        }

        return true
    }

    fun hideSoftKeyboard(view: View, context: Context) {
        val imm: InputMethodManager? =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }


}