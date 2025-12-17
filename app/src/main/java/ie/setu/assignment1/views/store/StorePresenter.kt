package ie.setu.assignment1.views.store

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import ie.setu.assignment1.main.MainApp
import ie.setu.assignment1.models.Location
import ie.setu.assignment1.models.StoreModel
import ie.setu.assignment1.views.editlocation.EditLocationView
import timber.log.Timber

class StorePresenter(private val view: StoreView) {

    var store = StoreModel()
    var app: MainApp = view.application as MainApp
    private lateinit var imageIntentLauncher : ActivityResultLauncher<PickVisualMediaRequest>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    var edit = false

    init {
        if (view.intent.hasExtra("store_edit")) {
            edit = true
            store = view.intent.extras?.getParcelable("store_edit")!!
            view.showStore(store)
        }
        registerImagePickerCallback()
        registerMapCallback()
    }

    fun doAddOrSave(title: String, description: String, dayOfMonth: Int, month: Int, year: Int, rating: Float) {
        store.name = title
        store.description = description
        store.lastVisitYear = year
        store.lastVisitMonth = month
        store.lastVisitDay = dayOfMonth
        store.rating = rating
        if (edit) {
            app.stores.update(store)
        } else {
            app.stores.create(store)
        }
        view.setResult(Activity.RESULT_OK)
        view.finish()
    }

    fun doCancel() {
        view.finish()
    }

    fun doDelete() {
        view.setResult(99)
        app.stores.delete(store)
        view.finish()
    }

    fun doSelectImage() {
        val request = PickVisualMediaRequest.Builder()
            .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly)
            .build()
        imageIntentLauncher.launch(request)
    }

    fun doSetLocation() {
        val location = Location(52.245696, -7.139102, 15f)
        if (store.zoom != 0f) {
            location.lat =  store.lat
            location.lng = store.lng
            location.zoom = store.zoom
        }
        val launcherIntent = Intent(view, EditLocationView::class.java)
            .putExtra("location", location)
        mapIntentLauncher.launch(launcherIntent)
    }

    fun cacheStore (title: String, description: String, dayOfMonth: Int, month: Int, year: Int, rating: Float) {
        store.name = title
        store.description = description
        store.lastVisitYear = year
        store.lastVisitMonth = month
        store.lastVisitDay = dayOfMonth
        store.rating = rating
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher = view.registerForActivityResult(
            ActivityResultContracts.PickVisualMedia()
        ) {
            try{
                view.contentResolver
                    .takePersistableUriPermission(it!!,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION )
                store.image = it // The returned Uri
                Timber.i("IMG :: ${store.image}")
                view.updateImage(store.image)
            }
            catch(e:Exception){
                e.printStackTrace()
            }
        }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Location ${result.data.toString()}")
                            //val location = result.data!!.extras?.getParcelable("location",Location::class.java)!!
                            val location = result.data!!.extras?.getParcelable<Location>("location")!!
                            Timber.i("Location == $location")
                            store.lat = location.lat
                            store.lng = location.lng
                            store.zoom = location.zoom
                        }
                    }
                    AppCompatActivity.RESULT_CANCELED -> { } else -> { }
                }
            }
    }
}