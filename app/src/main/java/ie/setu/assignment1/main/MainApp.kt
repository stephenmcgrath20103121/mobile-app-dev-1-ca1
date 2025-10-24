package ie.setu.assignment1.main

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import ie.setu.assignment1.models.StoreMemCollection
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    val stores = StoreMemCollection()

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        Timber.plant(Timber.DebugTree())
        i("Store started")
        //stores.add(StoreModel("One", "About one..."))
        //stores.add(StoreModel("Two", "About two..."))
        //stores.add(StoreModel("Three", "About three..."))
    }
}