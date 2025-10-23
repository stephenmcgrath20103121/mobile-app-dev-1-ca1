package ie.setu.assignment1.models

import timber.log.Timber.i

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
            logAll()
        }
    }

    private fun logAll() {
        stores.forEach { i("$it") }
    }
}