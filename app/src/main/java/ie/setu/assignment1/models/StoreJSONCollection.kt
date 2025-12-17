package ie.setu.assignment1.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import ie.setu.assignment1.helpers.*
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*

const val JSON_FILE = "stores.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<StoreModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class StoreJSONCollection(private val context: Context) : StoreCollection {

    var stores = mutableListOf<StoreModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<StoreModel> {
        logAll()
        return stores
    }

    override fun create(store: StoreModel) {
        store.id = generateRandomId()
        stores.add(store)
        serialize()
    }


    override fun update(store: StoreModel) {
        val storesList = findAll() as ArrayList<StoreModel>
        var foundStore: StoreModel? = storesList.find { s -> s.id == store.id }
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
        }
        serialize()
    }

    override fun delete(store: StoreModel) {
        stores.remove(store)
        serialize()
    }

    override fun findById(id:Long) : StoreModel? {
        val foundStore: StoreModel? = stores.find { it.id == id }
        return foundStore
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(stores, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        stores = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun logAll() {
        stores.forEach { Timber.i("$it") }
    }
}

class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}