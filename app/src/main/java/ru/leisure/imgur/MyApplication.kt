package ru.leisure.imgur

import android.app.Application
import android.content.Context
import ru.leisure.imgur.di.DiContainer

class MyApplication : Application() {

    lateinit var diContainer: DiContainer

    override fun onCreate() {
        super.onCreate()
        diContainer = DiContainer()
    }

    companion object {

        fun diContainer(context: Context) =
            (context.applicationContext as MyApplication).diContainer
    }
}