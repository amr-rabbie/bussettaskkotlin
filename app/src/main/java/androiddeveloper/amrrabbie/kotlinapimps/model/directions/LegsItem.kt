package androiddeveloper.amrrabbie.kotlinapimps.model.directions

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LegsItem(

	@field:SerializedName("duration")
	val duration: Duration,

	@field:SerializedName("start_location")
	val startLocation: StartLocation,

	@field:SerializedName("distance")
	val distance: Distance,

	@field:SerializedName("start_address")
	val startAddress: String,

	@field:SerializedName("end_location")
	val endLocation: EndLocation,

	@field:SerializedName("end_address")
	val endAddress: String,

	@field:SerializedName("via_waypoint")
	val viaWaypoint: List<String>,

	@field:SerializedName("steps")
	val steps: List<StepsItem>,

	@field:SerializedName("traffic_speed_entry")
	val trafficSpeedEntry: List<String>
) : Parcelable