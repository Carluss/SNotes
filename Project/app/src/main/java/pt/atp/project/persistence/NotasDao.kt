package pt.atp.project.persistence

import androidx.room.*

@Dao
interface NotasDao {

    @Query("SELECT * FROM notas ORDER BY date_time DESC")
    suspend fun getAllNotas() : List<Notas>

    @Query("SELECT * FROM notas WHERE id =:id")
    suspend fun getSpecificNota(id:Int) : Notas

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotas(note:Notas)

    @Delete
    suspend fun deleteNota(note:Notas)

    @Query("DELETE FROM notas WHERE id =:id")
    suspend fun deleteSpecificNota(id:Int)

    @Update
    suspend fun updateNote(note:Notas)
}