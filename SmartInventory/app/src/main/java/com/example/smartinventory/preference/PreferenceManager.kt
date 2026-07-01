package com.example.smartinventory.preference

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("smart_inventory_prefs", Context.MODE_PRIVATE)

    fun saveLoginStatus(isLoggedIn: Boolean) {
        prefs.edit().putBoolean("is_logged_in", isLoggedIn).apply()
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean("is_logged_in", false)
    }

    fun saveUser(id: String, username: String, role: String) {
        prefs.edit().apply {
            putString("user_id", id)
            putString("username", username)
            putString("role", role)
            apply()
        }
    }

    fun clear() {
        prefs.edit().clear().apply()
    }

    fun getUsername(): String? = prefs.getString("username", "")
    fun getRole(): String? = prefs.getString("role", "staff")
}
