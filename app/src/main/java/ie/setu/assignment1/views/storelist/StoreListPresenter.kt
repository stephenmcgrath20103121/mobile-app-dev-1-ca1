package ie.setu.assignment1.views.storelist

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import ie.setu.assignment1.views.map.StoreMapView
import ie.setu.assignment1.main.MainApp
import ie.setu.assignment1.models.StoreModel
import ie.setu.assignment1.views.store.StoreView

class StoreListPresenter(val view: StoreListView) {

    var app: MainApp
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    private var position: Int = 0

    init {
        app = view.application as MainApp
        registerMapCallback()
        registerRefreshCallback()
    }

    fun getStores() = app.stores.findAll()

    fun doAddStore() {
        val launcherIntent = Intent(view, StoreView::class.java)
        refreshIntentLauncher.launch(launcherIntent)
    }

    fun doEditStore(store: StoreModel, pos: Int) {
        val launcherIntent = Intent(view, StoreView::class.java)
        launcherIntent.putExtra("store_edit", store)
        position = pos
        refreshIntentLauncher.launch(launcherIntent)
    }

    fun doShowStoresMap() {
        val launcherIntent = Intent(view, StoreMapView::class.java)
        mapIntentLauncher.launch(launcherIntent)
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            view.registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) {
                if (it.resultCode == Activity.RESULT_OK) view.onRefresh()
                else // Deleting
                    if (it.resultCode == 99) view.onDelete(position)
            }
    }
    private fun registerMapCallback() {
        mapIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {  }
    }
}