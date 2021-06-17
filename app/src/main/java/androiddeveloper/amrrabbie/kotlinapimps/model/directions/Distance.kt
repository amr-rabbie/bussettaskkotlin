package androiddeveloper.amrrabbie.kotlinapimps.model.directions

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Distance(

	@field:SerializedName("text")
	val text: String,

	@field:SerializedName("value")
	val value: Int
) : Parcelable