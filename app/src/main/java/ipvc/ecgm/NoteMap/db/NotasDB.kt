package ipvc.ecgm.NoteMap.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import ipvc.ecgm.NoteMap.DAO.NotasDao
import ipvc.ecgm.NoteMap.entities.Nota
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Nota::class), version = 3, exportSchema = false)
public abstract class NotasDB : RoomDatabase() {

    abstract fun notasDao(): NotasDao

    private class NotasDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback(){

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var notasDao = database.notasDao()

                    //notasDao.deleteAll()

                    /*var notas = Notas(1, "Radar", "Papanata")
                    notasDao.insert(notas)
                    notas = Notas(2, "Buracos na Estrada", "Rua do Atlântico")
                    notasDao.insert(notas)*/
                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: NotasDB? = null

        fun getDatabase(context: Context, scope: CoroutineScope): NotasDB {
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotasDB::class.java,
                    "notas_database",
                )
                    //estratégia de destruição
                    //.fallbackToDestructiveMigration()
                    .addCallback(NotasDatabaseCallback(scope))
                    .build()

                INSTANCE = instance
                return instance

            }
        }
    }
}