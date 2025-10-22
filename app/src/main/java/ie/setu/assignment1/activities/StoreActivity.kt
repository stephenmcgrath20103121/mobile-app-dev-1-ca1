package ie.setu.assignment1.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import ie.setu.assignment1.R
import ie.setu.assignment1.databinding.ActivityStoreBinding
import ie.setu.assignment1.main.MainApp
import ie.setu.assignment1.models.StoreModel
import timber.log.Timber.i

class StoreActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStoreBinding
    var store = StoreModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)
        app = application as MainApp
        i("Store Activity started...")

        binding.btnAdd.setOnClickListener() {
            store.title = binding.storeTitle.text.toString()
            store.description = binding.description.text.toString()
            if (store.title.isNotEmpty()) {
                app.stores.add(store.copy())
                for (i in app.stores.indices) {
                    i("Store[$i]:${this.app.stores[i]}")
                }
                setResult(RESULT_OK)
                finish()
                }
            else {
                Snackbar
                    .make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_store, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}