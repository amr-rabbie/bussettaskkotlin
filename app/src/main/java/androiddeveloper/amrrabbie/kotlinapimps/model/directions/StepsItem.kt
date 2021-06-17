package androiddeveloper.amrrabbie.kotlinapimps.model.directions

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StepsItem(

	@field:SerializedName("duration")
	val duration: Duration,

	@field:SerializedName("start_location")
	val startLocation: StartLocation,

	@field:SerializedName("distance")
	val distance: Distance,

	@field:SerializedName("travel_mode")
	val travelMode: String,

	@field:SerializedName("html_instructions")
	val htmlInstructions: String,

	@field:SerializedName("end_location")
	val endLocation: EndLocation,

	@field:SerializedName("maneuver")
	val maneuver: String,

	@field:SerializedName("polyline")
	val polyline: Polyline
) : Parcelable