package ipvc.ecgm.NoteMap

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import ipvc.ecgm.NoteMap.api.EndPoints
import ipvc.ecgm.NoteMap.api.OutputLogin
import ipvc.ecgm.NoteMap.api.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login : AppCompatActivity() {

    private lateinit var editTextLoginName: EditText
    private lateinit var editTextLoginPass: EditText
    private lateinit var checkBox: CheckBox
    private lateinit var shared_preferences: SharedPreferences
    private var lembrar = false

    //despiste
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        editTextLoginName = findViewById(R.id.edTextNome)
        editTextLoginPass = findViewById(R.id.edTextPass)
        checkBox = findViewById(R.id.checkManterIni)

        shared_preferences = getSharedPreferences("shared_preferences", Context.MODE_PRIVATE)
        lembrar = shared_preferences.getBoolean("lembrar", false)

        if(lembrar){
            val intent = Intent(this@Login, Menu::class.java)
            startActivity(intent);
            finish()
        }
    }

    fun btLogin(view: View) {
        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val username = editTextLoginName.text.toString()
        val password = editTextLoginPass.text.toString()
        val checked_remember: Boolean = checkBox.isChecked
        val call = request.login(username = username, password = password)

        call.enqueue(object : Callback<OutputLogin> {
            override fun onResponse(call: Call<OutputLogin>, response: Response<OutputLogin>){
                if (response.isSuccessful){
                    val c: OutputLogin = response.body()!!
                    if(TextUtils.isEmpty(editTextLoginName.text) || TextUtils.isEmpty(editTextLoginPass.text)) {
                        Toast.makeText(this@Login, "Campos vazios", Toast.LENGTH_LONG).show()
                    }else{
                        if(c.status == "false"){
                            Toast.makeText(this@Login, c.MSG, Toast.LENGTH_LONG).show()
                        }else{
                            val shared_preferences_edit : SharedPreferences.Editor = shared_preferences.edit()
                            shared_preferences_edit.putString("username", username)
                            shared_preferences_edit.putString("password", password)
                            shared_preferences_edit.putInt("id", c.id)
                            shared_preferences_edit.putBoolean("lembrar", checked_remember)
                            shared_preferences_edit.apply()

                            val intent = Intent(this@Login, Menu::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            }
            override fun onFailure(call: Call<OutputLogin>, t: Throwable){
                Toast.makeText(this@Login,"${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun btVoltar(view: View) {
        val intent = Intent(this@Login, MainActivity::class.java)
        startActivity(intent)
    }
}