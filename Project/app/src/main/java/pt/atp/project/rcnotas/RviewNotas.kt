package pt.atp.project.rcnotas


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pt.atp.project.R
import pt.atp.project.persistence.*
import kotlinx.android.synthetic.main.unit_nota.view.*
import kotlin.collections.ArrayList

class RviewNotas() : RecyclerView.Adapter<RviewNotas.NotesViewHolder>() {
    var listener:OnItemClickListener? = null
    var arrList = ArrayList<Notas>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.unit_nota,parent,false)
        )
    }

    override fun getItemCount(): Int {
        return arrList.size
    }

    fun setData(arrNotesList: List<Notas>){
        arrList = arrNotesList as ArrayList<Notas>
    }

    fun setOnClickListener(listener1: OnItemClickListener){
        listener = listener1
    }



    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {

        holder.itemView.Title.text = arrList[position].titlo
        holder.itemView.Desc.text = arrList[position].texto
        holder.itemView.DateTime.text = arrList[position].dateTime


        holder.itemView.cardView.setOnClickListener {
            listener!!.onClicked(arrList[position].id!!)
        }

    }

    class NotesViewHolder(view:View) : RecyclerView.ViewHolder(view){}


    interface OnItemClickListener{ fun onClicked(notaId:Int) }

}