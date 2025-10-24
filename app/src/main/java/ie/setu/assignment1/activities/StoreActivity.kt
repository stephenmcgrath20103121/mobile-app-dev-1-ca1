package ie.setu.assignment1.activities

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.LayerDrawable
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
        val ratingStars = binding.ratingBar.progressDrawable as LayerDrawable
        ratingStars.getDrawable(2).setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_ATOP)

        if (intent.hasExtra("store_edit")) {
            edit = true
            store = intent.extras?.getParcelable("store_edit")!!
            binding.storeName.setText(store.name)
            binding.location.setText(store.location)
            binding.description.setText(store.description)
            binding.datePicker.updateDate(store.lastVisitYear,store.lastVisitMonth,store.lastVisitDay)
            binding.ratingBar.rating = store.rating
            binding.btnAdd.setText(R.string.save_store)
        }

        binding.btnAdd.setOnClickListener() {
            store.name = binding.storeName.text.toString()
            store.location = binding.location.text.toString()
            store.description = binding.description.text.toString()
            store.lastVisitYear = binding.datePicker.year
            store.lastVisitMonth = binding.datePicker.month
            store.lastVisitDay = binding.datePicker.dayOfMonth
            store.rating = binding.ratingBar.rating
            if (store.name.isEmpty()) {
                Snackbar.make(it,R.string.enter_store_name, Snackbar.LENGTH_LONG)
                    .show()
            }else if (store.location.isEmpty()) {
                Snackbar.make(it,R.string.enter_store_location, Snackbar.LENGTH_LONG)
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