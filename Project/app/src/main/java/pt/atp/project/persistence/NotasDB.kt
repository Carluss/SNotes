package pt.atp.project.persistence;


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Notas::class], version = 1, exportSchema = false)
abstract class NotasDB : RoomDatabase() {

    companion object {
        var notesDatabase: NotasDB? = null

        @Synchronized
        fun getDatabase(context: Context): NotasDB {
            if (notesDatabase == null) {
                notesDatabase = Room.databaseBuilder(
                    context
                    , NotasDB::class.java
                    , "notas.db"
                ).build()
            }
            return notesDatabase!!
        }
    }

    abstract fun noteDao():NotasDao
}