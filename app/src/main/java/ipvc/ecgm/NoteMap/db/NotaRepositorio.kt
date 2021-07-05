package ipvc.ecgm.NoteMap.db

import androidx.lifecycle.LiveData
import ipvc.ecgm.NoteMap.DAO.NotasDao
import ipvc.ecgm.NoteMap.entities.Nota

class NotaRepositorio(private val notasDao: NotasDao) {

    val allNotas: LiveData<List<Nota>> = notasDao.getNotas()

    suspend fun insert(nota: Nota) {
        notasDao.insert(nota)
    }

    suspend fun update(id: Int?, titulo: String, descricao: String){
        notasDao.update(id, titulo, descricao)
    }

    suspend fun delete(id: Int?){
        notasDao.delete(id)
    }

    /*suspend fun delete(id: Int){
        notasDao.deleteAll()
    }*/
}