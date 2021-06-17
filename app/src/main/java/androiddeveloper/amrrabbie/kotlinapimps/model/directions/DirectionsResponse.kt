package androiddeveloper.amrrabbie.kotlinapimps.model.directions

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DirectionsResponse(

	@field:SerializedName("routes")
	val routes: List<RoutesItem>,

	@field:SerializedName("geocoded_waypoints")
	val geocodedWaypoints: List<GeocodedWaypointsItem>,

	@field:SerializedName("status")
	val status: String
) : Parcelable