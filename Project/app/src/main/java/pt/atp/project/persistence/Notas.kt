package pt.atp.project.persistence

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName= "Notas")
class Notas : Serializable{

    @PrimaryKey(autoGenerate = true)
    var id:Int? =null

    @ColumnInfo(name = "titlo")
    var titlo:String? = null

    @ColumnInfo(name = "date_time")
    var dateTime:String? = null

    @ColumnInfo(name = "nota_desc")
    var texto:String? = null


}
