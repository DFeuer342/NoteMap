package ipvc.ecgm.NoteMap

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import ipvc.ecgm.NoteMap.api.EndPoints
import ipvc.ecgm.NoteMap.api.Reporte
import ipvc.ecgm.NoteMap.api.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var shared_preferences: SharedPreferences
    private lateinit var  reportes: List<Reporte>
    private val LOCATION_PERMISSION_REQUEST_CODE = 1
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        shared_preferences = getSharedPreferences("shared_preferences", Context.MODE_PRIVATE)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getReportes()
        var coordenadas: LatLng
        val sessao_id = shared_preferences.getInt("id", 0)
        Log.d("Reportes", sessao_id.toString())
        call.enqueue(object : Callback<List<Reporte>> {
            override fun onResponse(call: Call<List<Reporte>>, response: Response<List<Reporte>>){
                if (response.isSuccessful){
                    Log.d("Reportes", response.body().toString())
                    reportes = response.body()!!
                    for (reporte in reportes){
                        coordenadas = LatLng(reporte.latitude.toDouble(), reporte.longitude.toDouble())
                        if(reporte.user_id == sessao_id){
                            mMap.addMarker(MarkerOptions().position(coordenadas).title(reporte.id.toString()).snippet(reporte.titulo + "-" + reporte.descricao))
                                .setIcon(
                                    BitmapDescriptorFactory.defaultMarker(
                                        BitmapDescriptorFactory.HUE_BLUE))
                        }else{
                            mMap.addMarker(MarkerOptions().position(coordenadas).title(reporte.id.toString()).snippet(reporte.titulo + "-" + reporte.descricao))
                        }
                    }
                }
            }
            override fun onFailure(call: Call<List<Reporte>>, t: Throwable){
                Toast.makeText(this@MapsActivity,"${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        setUpMap()
        /*// Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))*/
    }

    private fun setUpMap() {
        if(ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)

            return
        } else{
            mMap.isMyLocationEnabled = true
            fusedLocationClient.lastLocation.addOnSuccessListener(this) {location ->

                if(location != null){
                    lastLocation = location
                    //Toast.makeText(this@MapsActivity, lastLocation.toString(), Toast.LENGTH_SHORT).show()
                    val currentLatLng = LatLng(location.latitude, location.longitude)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
                }
            }
        }
    }
}