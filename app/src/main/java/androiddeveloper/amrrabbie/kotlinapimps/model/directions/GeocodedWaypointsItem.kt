package androiddeveloper.amrrabbie.kotlinapimps.model.directions

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GeocodedWaypointsItem(

	@field:SerializedName("types")
	val types: List<String>,

	@field:SerializedName("geocoder_status")
	val geocoderStatus: String,

	@field:SerializedName("place_id")
	val placeId: String
) : Parcelable