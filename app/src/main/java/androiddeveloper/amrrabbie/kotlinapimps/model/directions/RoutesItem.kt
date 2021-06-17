package androiddeveloper.amrrabbie.kotlinapimps.model.directions

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RoutesItem(

	@field:SerializedName("summary")
	val summary: String,

	@field:SerializedName("copyrights")
	val copyrights: String,

	@field:SerializedName("legs")
	val legs: List<LegsItem>,

	@field:SerializedName("warnings")
	val warnings: List<String>,

	@field:SerializedName("bounds")
	val bounds: Bounds,

	@field:SerializedName("overview_polyline")
	val overviewPolyline: OverviewPolyline,

	@field:SerializedName("waypoint_order")
	val waypointOrder: List<String>
) : Parcelable