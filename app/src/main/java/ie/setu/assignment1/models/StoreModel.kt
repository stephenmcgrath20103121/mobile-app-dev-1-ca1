package ie.setu.assignment1.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StoreModel(var id: Long = 0,
                      var name: String = "",
                      var rating: Float = 0f,
                      var lastVisitDay: Int = 1,
                      var lastVisitMonth: Int = 0,
                      var lastVisitYear: Int = 2025,
                      var description: String = "",
                      var image: Uri = Uri.EMPTY,
                      var lat : Double = 0.0,
                      var lng: Double = 0.0,
                      var zoom: Float = 0f): Parcelable

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable


