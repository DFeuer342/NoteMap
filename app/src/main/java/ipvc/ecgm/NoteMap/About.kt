package ipvc.ecgm.NoteMap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class About : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
    }

    fun onClickAboutReturn(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}