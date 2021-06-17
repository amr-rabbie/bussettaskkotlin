package androiddeveloper.amrrabbie.kotlinapimps

import android.Manifest.permission
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androiddeveloper.amrrabbie.kotlinapimps.adapters.NearestByAdapter
import androiddeveloper.amrrabbie.kotlinapimps.databinding.ActivityMainBinding
import androiddeveloper.amrrabbie.kotlinapimps.model.directions.*
import androiddeveloper.amrrabbie.kotlinapimps.model.directions.Distance
import androiddeveloper.amrrabbie.kotlinapimps.model.directions.Duration
import androiddeveloper.amrrabbie.kotlinapimps.model.nearestby.ResultsItem
import androiddeveloper.amrrabbie.kotlinapimps.utils.Constants
import androiddeveloper.amrrabbie.kotlinapimps.utils.GpsTracker
import androiddeveloper.amrrabbie.kotlinapimps.utils.Network
import androiddeveloper.amrrabbie.kotlinapimps.viewmodel.NearestByViewModel
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.maps.model.LatLng
import com.google.maps.DirectionsApi
import com.google.maps.DirectionsApiRequest
import com.google.maps.GeoApiContext
import com.google.maps.model.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() , OnMapReadyCallback, LocationListener , OnNearestByListClick {

    lateinit var binding: ActivityMainBinding
    val nearestByViewModel:NearestByViewModel by viewModels()
    lateinit var nearestByAdapter: NearestByAdapter

    private var mMap: GoogleMap? = null
    private var latitude = 0.0
    private var longitude = 0.0
    private var rlatitude = 0.0
    private var rlongitude = 0.0
    private var gpsTracker: GpsTracker? = null
    private var marker1: Marker? = null
    private var marker2: Marker? = null
    var restaddress: String? = null
    var restrantdata: ResultsItem? = null

    private var locationManager: LocationManager? = null
    private val PERMISSION_REQUEST_CODE = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment: SupportMapFragment? = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        if (mapFragment != null) {
            mapFragment.getMapAsync(this)
        }

        getLocation()

        if(longitude > 0 && latitude > 0)
        bindNearestList()

        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager


       if (!checkPermission()) {
            requestPermission()
        } else {
            locationManager!!.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                5000, 1f, this
            )
        }
    }

    private fun bindNearestList(){
        val location = "$latitude,$longitude"
        val radius = 5000
        val sensor = "true"
        val names="cruise"
        val types = "restaurant"
        val key = "AIzaSyAyKWcogS2vgE52G5ZBj9IXtgqQ7n3cP5A"

        nearestByViewModel.getNearestBy(location,radius,types,names,key)

        nearestByViewModel.nearestbylist.observe(this,{ nearestlist->
            if(nearestlist != null){
                nearestByAdapter=NearestByAdapter(this,nearestlist,this)
                binding.restrantsrecycler.apply {
                    adapter=nearestByAdapter
                    layoutManager=LinearLayoutManager(this@MainActivity)
                    visibility=View.VISIBLE
                }

                binding.pbar.visibility=View.GONE
            }
        })
    }


    override fun onLocationChanged(location: Location) {
        try {
            latitude = location.latitude
            longitude = location.longitude
            //LatLng mylastloc = new LatLng(mylastlat, mylastlong);
            //marker1.setPosition(driverloc);
            //if (mMap != null) {
            Log.i("MyLocation", "My last lication is : $latitude , $longitude")
            Toast.makeText(
                this@MainActivity,
                "My last lication is : $latitude , $longitude",
                Toast.LENGTH_SHORT
            ).show()


        } catch (e: Exception) {
        }
    }

    private fun checkPermission(): Boolean {
        val result =
            ContextCompat.checkSelfPermission(applicationContext, permission.ACCESS_FINE_LOCATION)
        /*int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        int result3 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);*/return result == PackageManager.PERMISSION_GRANTED /*&&
                result1 == PackageManager.PERMISSION_GRANTED &&
                result2 == PackageManager.PERMISSION_GRANTED &&
                result3 == PackageManager.PERMISSION_GRANTED*/
    }

    private fun requestPermission() {

        //ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, CAMERA, READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        ActivityCompat.requestPermissions(
            this,
            arrayOf(permission.ACCESS_FINE_LOCATION),
            200
        )
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray)
    {
        if (permissions != null) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
        when (requestCode) {
            200 -> if (grantResults.size > 0) {
                val locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                /*boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean readStorageAccepted = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccepted = grantResults[3] == PackageManager.PERMISSION_GRANTED;*/

                //if (locationAccepted && cameraAccepted && readStorageAccepted && writeStorageAccepted)
                if (locationAccepted) if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (shouldShowRequestPermissionRationale(permission.ACCESS_FINE_LOCATION)) {
                        showMessageOKCancel(
                            "التطبيق يحتاج بعض الصلاحيات"
                        ) { dialog, which ->
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                //requestPermissions(new String[]{ACCESS_FINE_LOCATION, CAMERA, READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE},
                                requestPermissions(
                                    arrayOf(permission.ACCESS_FINE_LOCATION),
                                    200
                                )
                            }
                        }
                        return
                    }
                }
            }
        }
    }



    private fun showMessageOKCancel(message: String, okListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(this@MainActivity)
            .setMessage(message)
            .setPositiveButton("تم", okListener)
            .setNegativeButton("الغاء", null)
            .create()
            .show()
    }

    private fun drawMpath() {
        try {
            mMap?.clear()
            val restloc = LatLng(rlatitude, rlongitude)
            //mMap.addMarker(new MarkerOptions().position(barcelona).title("First Place"));
            val height1 = 100
            val width1 = 75
            val bitmapdraw1 = resources.getDrawable(R.drawable.redmarker) as BitmapDrawable
            val b1 = bitmapdraw1.bitmap
            val smallMarker1 = Bitmap.createScaledBitmap(b1, width1, height1, false)
            val height = 100
            val width = 75
            val bitmapdraw = resources.getDrawable(R.drawable.blackmarker) as BitmapDrawable
            val b = bitmapdraw.bitmap
            val smallMarker = Bitmap.createScaledBitmap(b, width, height, false)
            marker1 = mMap?.addMarker(
                MarkerOptions().position(restloc).title(restaddress)
                    .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
            )
            val myloc = LatLng(latitude, longitude)
            //mMap.addMarker(new MarkerOptions().position(madrid).title("Second Place"));
            marker2 = mMap?.addMarker(
                MarkerOptions().position(myloc).title("My location now")
                    .icon(BitmapDescriptorFactory.fromBitmap(smallMarker1))
            )


            //Define list to get all latlng for the route
            val path: ArrayList<LatLng?> = ArrayList<LatLng?>()


            //Execute Directions API request


            //Execute Directions API request
            val context = GeoApiContext.Builder()
                .apiKey(Constants.Api_Key)
                .build()
            val origin: String = latitude.toString() + "," + longitude
            val destination: String = rlatitude.toString() + "," + rlongitude

            //DirectionsApiRequest req = DirectionsApi.getDirections(context, "41.385064,2.173403", "40.416775,-3.70379");
            val req: DirectionsApiRequest =
                DirectionsApi.getDirections(context, origin, destination)
            try {
                val res: DirectionsResult = req.await()

                //Loop through legs and steps to get encoded polylines of each step
                if (res.routes != null && res.routes.size > 0) {
                    val route: DirectionsRoute = res.routes.get(0)
                    if (route.legs != null) {
                        for (i in 0 until route.legs.size) {
                            val leg: DirectionsLeg = route.legs.get(i)
                            if (leg.steps != null) {
                                for (j in 0 until leg.steps.size) {
                                    val step: DirectionsStep = leg.steps.get(j)
                                    if (step.steps != null && step.steps.size > 0) {
                                        for (k in 0 until step.steps.size) {
                                            val step1: DirectionsStep = step.steps.get(k)
                                            val points1: EncodedPolyline = step1.polyline
                                            if (points1 != null) {
                                                //Decode polyline and add points to list of route coordinates
                                                val coords1: List<com.google.maps.model.LatLng> =
                                                    points1.decodePath()
                                                for (coord1 in coords1) {
                                                    path.add(LatLng(coord1.lat, coord1.lng))
                                                }
                                            }
                                        }
                                    } else {
                                        val points: EncodedPolyline = step.polyline
                                        if (points != null) {
                                            //Decode polyline and add points to list of route coordinates
                                            val coords: List<com.google.maps.model.LatLng> =
                                                points.decodePath()
                                            for (coord in coords) {
                                                path.add(LatLng(coord.lat, coord.lng))
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (ex: java.lang.Exception) {
                Log.e("MainActivity.TAG", ex.localizedMessage)
            }


            //Draw the polyline

            //Draw the polyline
            if (path.size > 0) {
                val opts = PolylineOptions().addAll(path).color(Color.BLACK).width(10f)
                mMap!!.addPolyline(opts)
            }


            mMap?.getUiSettings()?.setZoomControlsEnabled(true)
            val curloc = LatLng(latitude, longitude)
            mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(curloc, 13F))
            val key: String = Constants.Api_Key
            nearestByViewModel.getDirections(origin, destination, key)
            nearestByViewModel.directions.observe(this@MainActivity,
                Observer<DirectionsResponse> { googleMapsResponse ->
                    val route: RoutesItem? = googleMapsResponse?.routes?.get(0)
                    val leg: LegsItem? = route?.legs?.get(0)
                    val distance: Distance? = leg?.distance
                    val value: Int? = distance?.value
                    val Mydistance = (value?.div(1000))?.let { java.lang.Double.valueOf(it.toDouble()) }
                    val duration: Duration? = leg?.duration
                    val value1: Int? = duration?.value
                    val mymints = (value1?.div(60))?.let { java.lang.Double.valueOf(it.toDouble()) }
                    val builder = android.app.AlertDialog.Builder(this@MainActivity)
                    builder.setTitle("Distance and time")
                    builder.setMessage("Distance: $Mydistance Km\nDuration: $mymints Mins")
                    builder.setPositiveButton(
                        "ok"
                    ) { dialog, which -> dialog.dismiss() }
                    builder.create().show()
                })
        } catch (e: java.lang.Exception) {
        }
    }

    fun getLocation() {
        latitude = 30.0135812
        longitude = 31.2819673
        if (!Network.isNetworkAvailable(this@MainActivity)) {
            latitude = 30.0135812
            longitude = 31.2819673
            return
        } else {
            gpsTracker = GpsTracker(this@MainActivity)
            if (gpsTracker!!.canGetLocation()) {
                latitude = gpsTracker!!.getLatitude()
                longitude = gpsTracker!!.getLongitude()
            } else {
                gpsTracker!!.showSettingsAlert()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


        // Add a marker in my loc and move the camera
        val myloc = LatLng(latitude, longitude)
        mMap!!.addMarker(MarkerOptions().position(myloc).title("My location now"))
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(myloc));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(myloc));
        val cameraPosition = CameraPosition.Builder().target(myloc).zoom(16f).build()
        mMap!!.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        mMap!!.getUiSettings().setZoomControlsEnabled(true)

        // mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }

    override fun onBackPressed() {
        /*if (!shouldAllowBack()) {
            doSomething();
        } else {
            super.onBackPressed();
        }*/
        binding.cview.visibility = View.GONE
        mMap!!.clear()
        // Add a marker in my loc and move the camera
        val myloc = LatLng(latitude, longitude)
        mMap!!.addMarker(MarkerOptions().position(myloc).title("My location now"))
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(myloc));
        val cameraPosition = CameraPosition.Builder().target(myloc).zoom(16f).build()
        mMap!!.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        mMap!!.uiSettings.isZoomControlsEnabled = true
    }

    override fun OnListClick(item: ResultsItem) {
        if (item != null) {
            restrantdata = item
            binding.cview.visibility = View.VISIBLE
            binding.name.setText(item.name)
            val types: List<String> = item.types
            var resttypes = ""
            for (i in types.indices) {
                resttypes = if (i < types.size - 1) {
                    resttypes + types[i] + " - "
                } else {
                    resttypes + types[i]
                }
            }
            binding.type.text = resttypes

            Glide.with(this)
                .load(item.icon)
                .into(binding.img)
            rlatitude = item.geometry.location.lat
            rlongitude = item.geometry.location.lng
            restaddress = item.vicinity
            drawMpath()
        }
    }


}


