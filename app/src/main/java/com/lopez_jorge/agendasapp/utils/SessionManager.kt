package com.lopez_jorge.agendasapp.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private val PREF_NAME = "user_session"
    private val KEY_USERNAME = "username"
    private val KEY_IS_LOGGED_IN = "is_logged_in"

    private val sharedPref: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    // ✅ Cambia Int -> String aquí
    fun saveSession(username: String) {
        sharedPref.edit()
            .putString(KEY_USERNAME, username)
            .putBoolean(KEY_IS_LOGGED_IN, true)
            .apply()
    }

    fun isLoggedIn(): Boolean {
        return sharedPref.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun getUsername(): String? {
        return sharedPref.getString(KEY_USERNAME, null)
    }

    fun clearSession() {
        sharedPref.edit().clear().apply()
    }
}
