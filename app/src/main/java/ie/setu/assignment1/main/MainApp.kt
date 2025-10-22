package ie.setu.assignment1.main

import android.app.Application
import ie.setu.assignment1.models.StoreModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    val stores = ArrayList<StoreModel>()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Store started")
        //stores.add(StoreModel("One", "About one..."))
        //stores.add(StoreModel("Two", "About two..."))
        //stores.add(StoreModel("Three", "About three..."))
    }
}