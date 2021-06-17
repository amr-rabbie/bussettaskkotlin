package androiddeveloper.amrrabbie.kotlinapimps.model.nearestby

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PhotosItem(

	@field:SerializedName("photo_reference")
	val photoReference: String,

	@field:SerializedName("width")
	val width: Int,

	@field:SerializedName("html_attributions")
	val htmlAttributions: List<String>,

	@field:SerializedName("height")
	val height: Int
) : Parcelable