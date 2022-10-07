package com.roomdbsample.other.prefs

import android.content.Context
import android.content.SharedPreferences

class SharePrefs(context: Context) {
    private val prefsName = "prefs.${context.packageName}"
    private val prefs: SharedPreferences = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = prefs.edit()
    private val fcmToken = "fcmToken"
     private val TAG = "SharePrefs"

    fun saveString(key: String?, value: String?) {
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(key: String?): String? {
        return prefs.getString(key, "")
    }


    /*--function to clear preferences--*/
    fun clearSharedPreference() {
        editor.clear().apply()
    }
}