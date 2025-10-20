package com.classify.hublink

import android.app.Application
import com.classify.hublink.data.AppContainer
import com.classify.hublink.data.AppDataContainer

class HublinkApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}