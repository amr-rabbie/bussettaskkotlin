package androiddeveloper.amrrabbie.kotlinapimps.model.nearestby

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Geometry(

	@field:SerializedName("viewport")
	val viewport: Viewport,

	@field:SerializedName("location")
	val location: Location
) : Parcelable