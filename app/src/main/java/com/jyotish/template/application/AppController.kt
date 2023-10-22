package com.jyotish.template.application

import android.app.Application
import android.content.res.Resources

class AppController: Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        res = resources
    }
    companion object {
        lateinit var instance: AppController
        lateinit var res: Resources
    }
}