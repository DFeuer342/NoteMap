package ipvc.ecgm.NoteMap

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class AddNotas : AppCompatActivity() {
    private lateinit var insertTituloView: EditText
    private lateinit var insertDescricaoView: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notas)

        insertTituloView = findViewById(R.id.addNotaTitulo)
        insertDescricaoView = findViewById(R.id.addNotaDescricao)

        val btCancelar = findViewById<Button>(R.id.btCancelar)
        btCancelar.setOnClickListener {
            Toast.makeText(this, "Operação cancelada", Toast.LENGTH_SHORT).show()
            finish();
        }

        val btGuardar = findViewById<Button>(R.id.btGuardarNota)
        btGuardar.setOnClickListener{
            val replyIntent = Intent()
            if (TextUtils.isEmpty(insertTituloView.text) && TextUtils.isEmpty(insertDescricaoView.text)){
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else if(TextUtils.isEmpty(insertTituloView.text)){
                setResult(Activity.RESULT_CANCELED, replyIntent)
            }else if(TextUtils.isEmpty(insertDescricaoView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            }else{
                val id = insertTituloView.id
                replyIntent.putExtra(EXTRA_REPLY_ID, id)
                val titulo = insertTituloView.text.toString()
                replyIntent.putExtra(EXTRA_REPLY_TITLE, titulo)
                val descricao = insertDescricaoView.text.toString()
                replyIntent.putExtra(EXTRA_REPLY_CONTENT, descricao)
                setResult(Activity.RESULT_OK, replyIntent)
                Toast.makeText(this, "Teste call", Toast.LENGTH_SHORT).show()
            }
            finish()
        }
    }
    companion object {
        const val EXTRA_REPLY_ID = "id"
        const val EXTRA_REPLY_TITLE = "titulo"
        const val EXTRA_REPLY_CONTENT  = "descricao"
    }
}