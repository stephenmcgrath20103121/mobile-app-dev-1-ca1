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

class StoreActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStoreBinding
    var store = StoreModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var edit = false
        binding = ActivityStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)
        app = application as MainApp

        if (intent.hasExtra("store_edit")) {
            edit = true
            store = intent.extras?.getParcelable("store_edit")!!
            binding.storeName.setText(store.name)
            binding.description.setText(store.description)
            binding.btnAdd.setText(R.string.save_store)
        }

        binding.btnAdd.setOnClickListener() {
            store.name = binding.storeName.text.toString()
            store.description = binding.description.text.toString()
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
            setResult(RESULT_OK)
            finish()
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