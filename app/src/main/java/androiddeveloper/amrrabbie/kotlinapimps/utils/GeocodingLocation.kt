package androiddeveloper.amrrabbie.kotlinapimps.utils

import android.content.Context
import android.location.Geocoder
import androiddeveloper.amrrabbie.kotlinapimps.utils.GeocodingLocation
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import java.io.IOException
import java.lang.StringBuilder
import java.util.*

object GeocodingLocation {
    private const val TAG = "GeocodingLocation"
    fun getAddressFromLocation(
        locationAddress: String,
        context: Context?, handler: Handler?
    ) {
        val thread: Thread = object : Thread() {
            override fun run() {
                val geocoder = Geocoder(context, Locale.getDefault())
                var result: String? = null
                var lati = 0.0
                var longi = 0.0
                try {
                    val addressList = geocoder.getFromLocationName(locationAddress, 1)
                    if (addressList != null && addressList.size > 0) {
                        val address = addressList[0]
                        val sb = StringBuilder()
                        sb.append(address.latitude).append("\n")
                        sb.append(address.longitude).append("\n")
                        result = sb.toString()
                        lati = address.latitude
                        longi = address.longitude
                    }
                } catch (e: IOException) {
                    Log.e(TAG, "Unable to connect to Geocoder", e)
                } finally {
                    val message = Message.obtain()
                    message.target = handler
                    if (result != null) {
                        message.what = 1
                        val bundle = Bundle()
                        result = """
                            Address: $locationAddress
                            
                            Latitude and Longitude :
                            $result
                            """.trimIndent()
                        bundle.putString("address", result)
                        bundle.putDouble("latitude", lati)
                        bundle.putDouble("longitude", longi)
                        message.data = bundle
                    } else {
                        message.what = 1
                        val bundle = Bundle()
                        result = """Address: $locationAddress
 Unable to get Latitude and Longitude for this address location."""
                        bundle.putString("address", result)
                        message.data = bundle
                    }
                    message.sendToTarget()
                }
            }
        }
        thread.start()
    }
}