package androiddeveloper.amrrabbie.kotlinapimps.model.nearestby

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlusCode(

	@field:SerializedName("compound_code")
	val compoundCode: String,

	@field:SerializedName("global_code")
	val globalCode: String
) : Parcelable