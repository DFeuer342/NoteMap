package ipvc.ecgm.NoteMap.api

data class Reporte(
    val id: Int,
    val latitude: Float,
    val longitude: Float,
    val tipo: String,
    val descricao: String,
    val user_id: Int
)
