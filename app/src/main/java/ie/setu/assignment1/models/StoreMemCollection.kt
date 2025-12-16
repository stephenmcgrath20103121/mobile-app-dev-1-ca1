package ie.setu.assignment1.models

import android.content.Context
import timber.log.Timber.i
import java.io.File

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class StoreMemCollection : StoreCollection {

    val stores = ArrayList<StoreModel>()

    override fun findAll(): List<StoreModel> {
        return stores
    }

    override fun create(store: StoreModel) {
        store.id = getId()
        stores.add(store)
        logAll()
    }

    override fun update(store: StoreModel) {
        var foundStore: StoreModel? = stores.find { s -> s.id == store.id }
        if (foundStore != null) {
            foundStore.name = store.name
            foundStore.description = store.description
            foundStore.lastVisitYear = store.lastVisitYear
            foundStore.lastVisitMonth = store.lastVisitMonth
            foundStore.lastVisitDay = store.lastVisitDay
            foundStore.rating = store.rating
            foundStore.image = store.image
            foundStore.lat = store.lat
            foundStore.lng = store.lng
            foundStore.zoom = store.zoom
            logAll()
        }
    }

    override fun delete(store: StoreModel) {
        var foundStore: StoreModel? = stores.find { s -> s.id == store.id }
        if (foundStore != null) {
            stores.remove(foundStore)
            logAll()
        }
    }

    private fun logAll() {
        stores.forEach { i("$it") }
    }
}