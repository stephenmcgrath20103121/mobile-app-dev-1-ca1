package ie.setu.assignment1.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import ie.setu.assignment1.databinding.ActivityStoreBinding
import ie.setu.assignment1.models.StoreModel
import timber.log.Timber
import timber.log.Timber.i

class StoreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoreBinding
    var store = StoreModel()
    val stores = ArrayList<StoreModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Timber.plant(Timber.DebugTree())
        i("Store Activity started...")

        binding.btnAdd.setOnClickListener() {
            store.title = binding.storeTitle.text.toString()
            store.description = binding.description.text.toString()
            if (store.title.isNotEmpty()) {
                stores.add(store.copy())
                i("add Button Pressed: $store")
                for (i in stores.indices)
                    { i("Store[$i]:${this.stores[i]}") }
            }
            else {
                Snackbar
                    .make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}