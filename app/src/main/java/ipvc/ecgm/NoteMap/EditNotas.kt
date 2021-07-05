package ipvc.ecgm.NoteMap

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText

class EditNotas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_notas)
        var editTituloView: EditText = findViewById(R.id.textTituloEdit)
        var editDescricao: EditText = findViewById(R.id.textDescEdit)

        var id = intent.getStringExtra(PARAM_ID)
        var titulo = intent.getStringExtra(PARAM_TITULO)
        var descricao = intent.getStringExtra(PARAM_DESCRICAO)
        editTituloView.setText(titulo.toString())
        editDescricao.setText(descricao.toString())

        val button = findViewById<Button>(R.id.btGuardarNota)
        button.setOnClickListener {
            val replyIntent = Intent()
            replyIntent.putExtra(EDIT_ID, id)
            if (TextUtils.isEmpty(editTituloView.text)  || TextUtils.isEmpty(editDescricao.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val editTitulo = editTituloView.text.toString()
                replyIntent.putExtra(EDIT_TITULO, editTitulo)
                //replyIntent.putExtra(STATUS, "EDIT")
                //setResult(Activity.RESULT_OK, replyIntent)
                val editDesc = editDescricao.text.toString()
                replyIntent.putExtra(EDIT_DESCRICAO, editDesc)
                replyIntent.putExtra(STATUS, "EDIT")
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }

        val button_delete = findViewById<Button>(R.id.btApagarNota)
        button_delete.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editTituloView.text)  || TextUtils.isEmpty(editDescricao.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                replyIntent.putExtra(DELETE_ID, id)
                replyIntent.putExtra(STATUS, "DELETE")
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val STATUS = ""
        const val DELETE_ID = "DELETE_ID"
        const val EDIT_ID = "EDIT_ID"
        const val EDIT_TITULO = "EDIT_TITULO"
        const val EDIT_DESCRICAO = "EDIT_DESCRICAO"
    }
}