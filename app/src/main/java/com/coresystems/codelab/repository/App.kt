package com.coresystems.codelab.repository

import android.app.Application

/**
 * Extension of the Android Application class.
 *
 * Created by coresystems on 20.09.17.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Repository.create(this)
    }
}