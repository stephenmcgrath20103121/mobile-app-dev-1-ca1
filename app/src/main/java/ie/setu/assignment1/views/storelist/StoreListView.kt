package ie.setu.assignment1.views.storelist

import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import ie.setu.assignment1.R
import ie.setu.assignment1.databinding.ActivityStoreListBinding
import ie.setu.assignment1.main.MainApp
import ie.setu.assignment1.models.StoreModel

class StoreListView : AppCompatActivity(), StoreListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityStoreListBinding
    lateinit var presenter: StoreListPresenter
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityStoreListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        presenter = StoreListPresenter(this)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        loadStores()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> { presenter.doAddStore() }
            R.id.item_map -> { presenter.doShowStoresMap() }
            R.id.light_dark_toggle -> { doToggleLightDarkMode() }
        }
        return super.onOptionsItemSelected(item)
    }

    fun doToggleLightDarkMode() {
        val darkModeFlags = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK// Retrieve the Mode of the App.
        val isDarkModeOn = darkModeFlags == Configuration.UI_MODE_NIGHT_YES//Check if the Dark Mode is On
        if (isDarkModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)//Switch off the dark mode.
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

    override fun onStoreClick(store: StoreModel, position: Int) {
        this.position = position
        presenter.doEditStore(store, this.position)
    }

    private fun loadStores() {
        binding.recyclerView.adapter = StoreAdapter(presenter.getStores(), this)
        onRefresh()
    }

    fun onRefresh() {
        binding.recyclerView.adapter?.
        notifyItemRangeChanged(0,presenter.getStores().size)
    }

    fun onDelete(position : Int) {
        binding.recyclerView.adapter?.notifyItemRemoved(position)
    }
}