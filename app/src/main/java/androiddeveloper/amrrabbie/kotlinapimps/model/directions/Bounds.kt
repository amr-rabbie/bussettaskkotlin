package androiddeveloper.amrrabbie.kotlinapimps.model.directions

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Bounds(

	@field:SerializedName("southwest")
	val southwest: Southwest,

	@field:SerializedName("northeast")
	val northeast: Northeast
) : Parcelable