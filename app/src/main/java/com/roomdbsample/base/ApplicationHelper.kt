package com.roomdbsample.base

import android.app.Application
import com.roomdbsample.other.AppConstants.IS_MENU_SHOW
import com.roomdbsample.other.AppConstants.LIST_VIEW
import com.roomdbsample.other.prefs.SharePrefs

class ApplicationHelper:Application() {
    companion object {
        var mInstance: ApplicationHelper? = null
        var prefs: SharePrefs? = null
        @Synchronized
        fun getInstance(): ApplicationHelper? {
            return mInstance
        }
    }
    override fun onCreate() {
        super.onCreate()
        mInstance = this
        prefs = SharePrefs(applicationContext)
        if (prefs!!.getString(IS_MENU_SHOW)!!.isEmpty()){
            prefs!!.saveString(IS_MENU_SHOW,LIST_VIEW)
        }
    }
}