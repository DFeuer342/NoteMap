package ipvc.ecgm.NoteMap

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ipvc.ecgm.NoteMap.adapters.NotaAdapter
import ipvc.ecgm.NoteMap.entities.Nota
import ipvc.ecgm.NoteMap.viewModel.CellClickListener
import ipvc.ecgm.NoteMap.viewModel.NotaViewModel

const val PARAM_ID: String = "id"
const val PARAM_TITULO: String = "titulo"
const val PARAM_DESCRICAO: String = "descricao"

class ListaNotas : AppCompatActivity(), CellClickListener {
    private val newNotasActivityRequestCode = 1
    private val newNotasActivityRequestCode2 = 2


    val NotaViewModel: NotaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("ver", "Linha 32")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_notas)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = NotaAdapter(this, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val viewModel = ViewModelProvider(this).get(NotaViewModel::class.java)
        NotaViewModel.allNotas.observe(this, Observer { notas ->
            notas?.let { adapter.setNotas(it) }
        })

        Log.d("ver", "Linha 46")
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            Log.d("ver", "Linha 49")
            val intent = Intent(this@ListaNotas, AddNotas::class.java)
            startActivityForResult(intent, newNotasActivityRequestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == newNotasActivityRequestCode) {
            if (requestCode == newNotasActivityRequestCode && resultCode == Activity.RESULT_OK) {
                var titulo = data?.getStringExtra(AddNotas.EXTRA_REPLY_TITLE).toString()
                var descricao = data?.getStringExtra(AddNotas.EXTRA_REPLY_CONTENT).toString()
                var nota = Nota(titulo = titulo, descricao = descricao)
                NotaViewModel.insert(nota)
                Toast.makeText(this, "Nota guardada.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    applicationContext,
                    "Dados vazios. Operação cancelada",
                    Toast.LENGTH_LONG).show()
                Toast.makeText(this, "Nota guardada.", Toast.LENGTH_SHORT).show()
            }
        }
        // EDITAR E APAGAR NOTA
        if (requestCode == newNotasActivityRequestCode2 && resultCode == Activity.RESULT_OK) {
            var edit_titulo = data?.getStringExtra(EditNotas.EDIT_TITULO).toString()
            var edit_descricao = data?.getStringExtra(EditNotas.EDIT_DESCRICAO).toString()
            var id = data?.getStringExtra(EditNotas.EDIT_ID)
            var id_delete = data?.getStringExtra(EditNotas.DELETE_ID)
            if(data?.getStringExtra(EditNotas.STATUS) == "EDIT"){
                NotaViewModel.update(id?.toIntOrNull(), edit_titulo, edit_descricao)
                Toast.makeText(this, "NOTA EDITADA", Toast.LENGTH_SHORT).show()
            } else if(data?.getStringExtra(EditNotas.STATUS) == "DELETE"){
                NotaViewModel.delete(id_delete?.toIntOrNull())
                Toast.makeText(this, "NOTA ELIMINADA", Toast.LENGTH_SHORT).show()
            }
            //notasViewModel.update(id?.toIntOrNull(), edit_titulo, edit_observacao)
            //notasViewModel.delete(id_delete?.toIntOrNull())
            //Toast.makeText(this, "Nota editada!", Toast.LENGTH_LONG).show()
        } else if (resultCode == Activity.RESULT_CANCELED) {
            if(data?.getStringExtra(EditNotas.STATUS) == "EDIT"){
                Toast.makeText(this, "Erro a editar nota", Toast.LENGTH_SHORT).show()
            } else if(data?.getStringExtra(EditNotas.STATUS) == "DELETE"){
                Toast.makeText(this, "Erro a eliminar nota", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCellClickListener(data: Nota) {
        val intent = Intent(this@ListaNotas, EditNotas::class.java)
        intent.putExtra(PARAM_ID, data.id.toString())
        intent.putExtra(PARAM_TITULO, data.titulo.toString())
        intent.putExtra(PARAM_DESCRICAO, data.descricao.toString())
        startActivityForResult(intent, newNotasActivityRequestCode2)
        Log.d("Obser", data.id.toString())
        Toast.makeText(this,data.id.toString(), Toast.LENGTH_SHORT).show()
    }

}