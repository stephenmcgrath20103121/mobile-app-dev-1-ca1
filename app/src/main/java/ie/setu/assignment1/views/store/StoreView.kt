package ie.setu.assignment1.views.store

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import ie.setu.assignment1.R
import ie.setu.assignment1.databinding.ActivityStoreBinding
import ie.setu.assignment1.models.StoreModel
import timber.log.Timber
import androidx.appcompat.app.AlertDialog

class StoreView : AppCompatActivity() {

    private lateinit var binding: ActivityStoreBinding
    private lateinit var presenter: StorePresenter
    var store = StoreModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        presenter = StorePresenter(this)

        binding.chooseImage.setOnClickListener {
            presenter.cacheStore(binding.storeName.text.toString(), binding.description.text.toString(),binding.datePicker.dayOfMonth, binding.datePicker.month, binding.datePicker.year, binding.ratingBar.rating)
            presenter.doSelectImage()
        }

        binding.storeLocation.setOnClickListener {
            presenter.cacheStore(binding.storeName.text.toString(), binding.description.text.toString(), binding.datePicker.dayOfMonth, binding.datePicker.month, binding.datePicker.year, binding.ratingBar.rating)
            presenter.doSetLocation()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_store, menu)
        val deleteMenu: MenuItem = menu.findItem(R.id.item_delete)
        deleteMenu.isVisible = presenter.edit
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_save -> {
                if (binding.storeName.text.toString().isEmpty()) {
                    Snackbar.make(binding.root, R.string.enter_store_name, Snackbar.LENGTH_LONG)
                        .show()
                } else {
                    presenter.doAddOrSave(binding.storeName.text.toString(), binding.description.text.toString(), binding.datePicker.dayOfMonth, binding.datePicker.month, binding.datePicker.year, binding.ratingBar.rating)
                }
            }
            R.id.item_delete -> {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Are you sure you want to delete this store?")
                builder.setTitle("Confirm Deletion")
                builder.setCancelable(false)
                builder.setPositiveButton("Delete") {
                    dialog, which -> presenter.doDelete()
                }

                builder.setNegativeButton("Cancel") {
                    dialog, which -> dialog.cancel()
                }

                val alertDialog = builder.create()
                alertDialog.show()
            }
            R.id.item_cancel -> {
                presenter.doCancel()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun showStore(store: StoreModel) {
        binding.storeName.setText(store.name)
        binding.description.setText(store.description)
        binding.datePicker.updateDate(store.lastVisitYear, store.lastVisitMonth, store.lastVisitDay)
        binding.ratingBar.rating=store.rating
        Picasso.get()
            .load(store.image)
            .into(binding.storeImage)
        if (store.image != Uri.EMPTY) {
            binding.chooseImage.setText(R.string.change_store_image)
        }

    }

    fun updateImage(image: Uri){
        Timber.i("Image updated")
        Picasso.get()
            .load(image)
            .into(binding.storeImage)
        binding.chooseImage.setText(R.string.change_store_image)
    }

}