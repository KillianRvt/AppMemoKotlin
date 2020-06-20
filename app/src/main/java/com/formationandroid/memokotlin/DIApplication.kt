package com.formationandroid.memokotlin

import android.app.Application
import di.AppComponent
import di.DaggerAppComponent

class DIApplicationJ : Application() {
    private var appComponent: AppComponent? = null
    override fun onCreate() {

        // initialisation :
        super.onCreate()
        instance = this
        // dagger :
        appComponent = DaggerAppComponent.builder().application(this)?.build()
    }

    companion object {
        // Attributs :
        private var instance: DIApplicationJ? = null

        // Getter singleton :
        fun getAppComponent(): AppComponent? {
            return instance!!.appComponent
        }
    }
}