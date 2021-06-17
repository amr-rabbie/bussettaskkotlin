package androiddeveloper.amrrabbie.kotlinapimps.network

import androiddeveloper.amrrabbie.kotlinapimps.model.directions.DirectionsResponse
import androiddeveloper.amrrabbie.kotlinapimps.model.nearestby.NearestByResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NearestByApiServices {

    @GET("place/nearbysearch/json")
    suspend fun getNearestBy(
        @Query("location") location:String,
        @Query("radius") radius:Int,
        @Query("types") types:String,
        @Query("name") name:String,
        @Query("key") key:String
    ):Response<NearestByResponse>

    @GET("directions/json")
    suspend fun getDirections(
        @Query("origin") origin:String,
        @Query("destination") destination:String,
        @Query("key") key:String
    ):Response<DirectionsResponse>
}