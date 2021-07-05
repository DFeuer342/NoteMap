package ipvc.ecgm.NoteMap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun btNotas(view: View) {
        val intent = Intent(this, ListaNotas::class.java)
        Log.d("ver", "passou")
        startActivity(intent)
    }

    fun btEntrar(view: View) {
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
    }

    fun btAbout(view: View) {
        val intent = Intent(this, About::class.java)
        startActivity(intent)
    }


}