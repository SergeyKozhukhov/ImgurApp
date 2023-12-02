package ru.leisure.imgur

import android.app.Application
import android.content.Context
import ru.leisure.imgur.di.AppComponent
import ru.leisure.imgur.di.DaggerAppComponent

class MyApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }

    companion object {

        fun appComponent(context: Context) =
            (context.applicationContext as MyApplication).appComponent
    }
}