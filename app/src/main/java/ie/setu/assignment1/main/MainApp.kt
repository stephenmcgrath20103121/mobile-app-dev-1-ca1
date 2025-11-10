package ie.setu.assignment1.main

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import ie.setu.assignment1.models.StoreMemCollection
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    lateinit var stores: StoreMemCollection

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        stores = StoreMemCollection(this)
        //stores.load()
        Timber.plant(Timber.DebugTree())
        i("Store started")
    }
}