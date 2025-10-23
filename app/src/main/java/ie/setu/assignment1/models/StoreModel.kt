package ie.setu.assignment1.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StoreModel(var id: Long = 0,
                      var name: String = "",
                      var description: String = ""): Parcelable
