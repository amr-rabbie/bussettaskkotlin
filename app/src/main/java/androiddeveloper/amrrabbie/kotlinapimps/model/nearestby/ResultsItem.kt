package androiddeveloper.amrrabbie.kotlinapimps.model.nearestby

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResultsItem(

	@field:SerializedName("types")
	val types: List<String>,

	@field:SerializedName("business_status")
	val businessStatus: String,

	@field:SerializedName("icon")
	val icon: String,

	@field:SerializedName("rating")
	val rating: Double,

	@field:SerializedName("photos")
	val photos: List<PhotosItem>,

	@field:SerializedName("reference")
	val reference: String,

	@field:SerializedName("user_ratings_total")
	val userRatingsTotal: Int,

	@field:SerializedName("scope")
	val scope: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("opening_hours")
	val openingHours: OpeningHours,

	@field:SerializedName("geometry")
	val geometry: Geometry,

	@field:SerializedName("vicinity")
	val vicinity: String,

	@field:SerializedName("plus_code")
	val plusCode: PlusCode,

	@field:SerializedName("place_id")
	val placeId: String,

	@field:SerializedName("permanently_closed")
	val permanentlyClosed: Boolean,

	@field:SerializedName("price_level")
	val priceLevel: Int
) : Parcelable