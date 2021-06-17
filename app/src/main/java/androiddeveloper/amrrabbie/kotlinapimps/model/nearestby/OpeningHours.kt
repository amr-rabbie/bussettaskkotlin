package androiddeveloper.amrrabbie.kotlinapimps.model.nearestby

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OpeningHours(

	@field:SerializedName("open_now")
	val openNow: Boolean
) : Parcelable