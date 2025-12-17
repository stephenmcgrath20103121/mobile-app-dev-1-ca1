package ie.setu.assignment1.activities

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.LayerDrawable
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import ie.setu.assignment1.R
import ie.setu.assignment1.databinding.ActivityStoreBinding
import ie.setu.assignment1.helpers.showImagePicker
import ie.setu.assignment1.main.MainApp
import ie.setu.assignment1.models.Location
import ie.setu.assignment1.models.StoreModel
import timber.log.Timber.i

class StoreActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStoreBinding
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    var store = StoreModel()
    lateinit var app: MainApp
    var edit = false
    private lateinit var imageIntentLauncher : ActivityResultLauncher<PickVisualMediaRequest>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)
        app = application as MainApp
        i("Store Activity started...")
        val ratingStars = binding.ratingBar.progressDrawable as LayerDrawable
        ratingStars.getDrawable(2).setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_ATOP)

        if (intent.hasExtra("store_edit")) {
            edit = true
            store = intent.extras?.getParcelable("store_edit")!!
            binding.storeName.setText(store.name)
            binding.description.setText(store.description)
            binding.datePicker.updateDate(store.lastVisitYear,store.lastVisitMonth,store.lastVisitDay)
            binding.ratingBar.rating = store.rating
            binding.btnAdd.setText(R.string.save_store)
            Picasso.get()
                .load(store.image)
                .into(binding.storeImage)
            if (store.image != Uri.EMPTY) {
                binding.chooseImage.setText(R.string.change_store_image)
            }
        }

        binding.btnAdd.setOnClickListener() {
            store.name = binding.storeName.text.toString()
            store.description = binding.description.text.toString()
            store.lastVisitYear = binding.datePicker.year
            store.lastVisitMonth = binding.datePicker.month
            store.lastVisitDay = binding.datePicker.dayOfMonth
            store.rating = binding.ratingBar.rating
            if (store.name.isEmpty()) {
                Snackbar.make(it,R.string.enter_store_name, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                if (edit) {
                    app.stores.update(store.copy())
                } else {
                    app.stores.create(store.copy())
                }
            }
            i("add Button Pressed: $store")
            setResult(RESULT_OK)
            finish()
        }

        binding.chooseImage.setOnClickListener {
            val request = PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly)
                .build()
            imageIntentLauncher.launch(request)
            i("Select image")
        }

        registerImagePickerCallback()

        binding.storeLocation.setOnClickListener {
            val location = Location(52.245696, -7.139102, 15f)
            if (store.zoom != 0f) {
                location.lat =  store.lat
                location.lng = store.lng
                location.zoom = store.zoom
            }
            val launcherIntent = Intent(this, MapActivity::class.java)
                .putExtra("location", location)
            mapIntentLauncher.launch(launcherIntent)
        }

        registerMapCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_store, menu)
        if (edit)
            menu.getItem(0).isVisible = true
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_delete -> {
                setResult(99)
                app.stores.delete(store)
                finish()
            }
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher = registerForActivityResult(
            ActivityResultContracts.PickVisualMedia()
        ) {
            try{
                contentResolver
                    .takePersistableUriPermission(it!!,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION )
                store.image = it
                i("IMG :: ${store.image}")
                Picasso.get()
                    .load(store.image)
                    .into(binding.storeImage)
            }
            catch(e:Exception){
                e.printStackTrace()
            }
        }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Location ${result.data.toString()}")
                            val location = result.data!!.extras?.getParcelable<Location>("location")!!
                            i("Location == $location")
                            store.lat = location.lat
                            store.lng = location.lng
                            store.zoom = location.zoom
                        }
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }

}