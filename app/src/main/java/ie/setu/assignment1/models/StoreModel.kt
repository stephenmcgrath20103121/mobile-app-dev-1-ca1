package ie.setu.assignment1.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StoreModel(var id: Long = 0,
                      var name: String = "",
                      var location: String = "",
                      var rating: Float = 0f,
                      var lastVisitDay: Int = 1,
                      var lastVisitMonth: Int = 0,
                      var lastVisitYear: Int = 2025,
                      var description: String = ""): Parcelable
