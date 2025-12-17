package ie.setu.assignment1.main

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import ie.setu.assignment1.models.StoreJSONCollection
import ie.setu.assignment1.models.StoreCollection
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    lateinit var stores: StoreCollection

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        Timber.plant(Timber.DebugTree())
        stores = StoreJSONCollection(applicationContext)
        i("Store started")
    }
}