package ie.setu.assignment1.models

import android.content.Context
import kotlinx.serialization.json.Json
import timber.log.Timber.i
import java.io.File

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class StoreMemCollection(context: Context) : StoreCollection {

    val stores = ArrayList<StoreModel>()
    val json = Json{prettyPrint = true}
    val file = File(context.filesDir, "stores.json")

    override fun findAll(): List<StoreModel> {
        return stores
    }

    override fun create(store: StoreModel) {
        store.id = getId()
        stores.add(store)
        logAll()
        save()
    }

    override fun update(store: StoreModel) {
        var foundStore: StoreModel? = stores.find { s -> s.id == store.id }
        if (foundStore != null) {
            foundStore.name = store.name
            foundStore.location = store.location
            foundStore.description = store.description
            foundStore.lastVisitYear = store.lastVisitYear
            foundStore.lastVisitMonth = store.lastVisitMonth
            foundStore.lastVisitDay = store.lastVisitDay
            foundStore.rating = store.rating
            logAll()
            save()
        }
    }

    override fun delete(store: StoreModel) {
        var foundStore: StoreModel? = stores.find { s -> s.id == store.id }
        if (foundStore != null) {
            stores.remove(foundStore)
            logAll()
            save()
        }
    }

    private fun logAll() {
        stores.forEach { i("$it") }
    }

    fun load() {
        if (file.exists()) {
            val text = file.readText()
            if (text.isNotBlank()) {
                stores.clear()
                stores.addAll(json.decodeFromString(text))
                lastId = if (stores.isEmpty()) 0L else stores.maxOf { it.id } + 1
            }
        }
    }

    private fun save() {
        file.writeText(json.encodeToString(stores))
    }
}