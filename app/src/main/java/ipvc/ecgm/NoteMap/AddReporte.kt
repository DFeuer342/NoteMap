package ipvc.ecgm.NoteMap

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import ipvc.ecgm.NoteMap.api.EndPoints
import ipvc.ecgm.NoteMap.api.OutputReporte
import ipvc.ecgm.NoteMap.api.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddReporte : AppCompatActivity() {

    private lateinit var reporteTextTitulo: EditText
    private lateinit var reporteTextDesc: EditText
    private lateinit var shared_preferences: SharedPreferences
    private var latitude : Double = 0.0
    private var longitude : Double = 0.0
    private val LOCATION_PERMISSION_REQUEST_CODE = 1
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var lastLocation: Location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_reporte)

        reporteTextTitulo = findViewById(R.id.reportTitulo)
        reporteTextDesc = findViewById(R.id.reportDesc)
        shared_preferences = getSharedPreferences("shared_preferences", Context.MODE_PRIVATE)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult?) {
                super.onLocationResult(p0)
                lastLocation = p0?.lastLocation!!
                latitude = lastLocation.latitude
                longitude = lastLocation.longitude
            }
        }
        createLocationRequest()
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.interval = 10000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    fun NovoRep(view: View) {
        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val latitude = latitude
        val longitude = longitude
        val titulo = reporteTextTitulo.text.toString()
        val descricao = reporteTextDesc.text.toString()
        val sessao_id = shared_preferences.getInt("id", 0)
        Log.d("Erro", "Linha68")
        Log.d("Erro", "id "+ sessao_id.toString())

        val call = request.setReportes(
                latitude = latitude.toString(),
                longitude = longitude.toString(),
                titulo = titulo,
                descricao = descricao,
                user_id = sessao_id)

        Log.d("Erro", "Linha76")
        Log.d("Erro", call.toString())

        call.enqueue(object : Callback<OutputReporte> {
            override fun onResponse(call: Call<OutputReporte>, response: Response<OutputReporte>) {
                if (response.isSuccessful) {
                    val resposta: OutputReporte = response.body()!!
                    Toast.makeText(this@AddReporte, resposta.MSG, Toast.LENGTH_LONG).show()
                    Log.d("Erro", "Linha84")
                    Log.d("Erro", resposta.MSG)
                    val intent = Intent(this@AddReporte, Menu::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            override fun onFailure(call: Call<OutputReporte>, t: Throwable) {
                Log.d("Erro", "Linha93")
                Log.d("Erro", "${t.message}")
            }
        })
    }

    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onResume() {
        super.onResume()
        startLocationUpdates()
    }

    private fun startLocationUpdates() {
        if(ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)

    }

    fun cancelReporte(view: View) {
        val intent = Intent(this@AddReporte, Menu::class.java)
        startActivity(intent)
        finish()
    }
}