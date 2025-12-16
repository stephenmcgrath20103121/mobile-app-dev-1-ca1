package ie.setu.assignment1.main

import android.app.Application
import ie.setu.assignment1.models.StoreJSONCollection
import ie.setu.assignment1.models.StoreMemCollection
import ie.setu.assignment1.models.StoreCollection
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    lateinit var stores: StoreCollection

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        stores = StoreJSONCollection(applicationContext)
        i("Store started")
    }
}