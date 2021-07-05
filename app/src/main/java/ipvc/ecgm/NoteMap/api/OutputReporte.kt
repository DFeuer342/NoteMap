package ipvc.ecgm.NoteMap.api

data class OutputReporte(
    val latitude: String,
    val longitude: String,
    val tipo: String,
    val descricao: String,
    val user_id: String,
    val status: String,
    val MSG: String
)