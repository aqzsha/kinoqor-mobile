package com.example.kinoqor.data.local
import android.content.Context
import android.content.SharedPreferences

class AuthPreferences(context: Context) {

    companion object {
        private const val PREF_NAME = "auth_prefs"
        private const val KEY_TOKEN = "token"
    }

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        prefs.edit().putString(KEY_TOKEN, token).apply()
    }

    fun getToken(): String? = prefs.getString(KEY_TOKEN, null)

    fun clearToken() {
        prefs.edit().remove(KEY_TOKEN).apply()
    }
}