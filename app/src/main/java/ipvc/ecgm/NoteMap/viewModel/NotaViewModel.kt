package ipvc.ecgm.NoteMap.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import ipvc.ecgm.NoteMap.db.NotasDB
import ipvc.ecgm.NoteMap.entities.Nota
import ipvc.ecgm.NoteMap.db.NotaRepositorio
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository : NotaRepositorio
    val allNotas : LiveData<List<Nota>>

    init {
        val notasDao = NotasDB.getDatabase(application, viewModelScope).notasDao()
        repository = NotaRepositorio(notasDao)
        allNotas = repository.allNotas
    }

    fun insert(notas: Nota) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(notas)
    }

    fun update(id: Int?, titulo: String, observacao: String) = viewModelScope.launch{
        repository.update(id, titulo, observacao)
    }

    fun delete(id: Int?) = viewModelScope.launch{
        repository.delete(id)
    }
}