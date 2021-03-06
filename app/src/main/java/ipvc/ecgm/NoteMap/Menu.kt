package ipvc.ecgm.NoteMap

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast

class Menu : AppCompatActivity() {

    private lateinit var shared_preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        shared_preferences = getSharedPreferences("shared_preferences", Context.MODE_PRIVATE)
    }

    fun btSair(view: View){
        val shared_preferences_edit : SharedPreferences.Editor = shared_preferences.edit()
        shared_preferences_edit.clear()
        shared_preferences_edit.apply()

        val intent = Intent(this@Menu, Login::class.java)
        startActivity(intent)
        finish()
    }

    fun btMapa(view: View) {
        val intent = Intent(this@Menu, MapsActivity::class.java)
        startActivity(intent)
    }
    fun btExtra(view: View) {
        Toast.makeText(this@Menu,"Activity Extra", Toast.LENGTH_SHORT).show()
    }

}