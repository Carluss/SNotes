package pt.atp.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_criar_lista.*
import kotlinx.coroutines.launch
import pt.atp.project.persistence.*
import java.text.SimpleDateFormat
import java.util.*


class CriarLista : SubFragment() {

    var currentDate:String? = null
    private var notaId = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_criar_lista, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(notaId != -1){
            launch {
                context?.let {
                    var notas = NotasDB.getDatabase(it).noteDao().getSpecificNota(notaId)

                    Title.setText(notas.titlo)
                    Desc.setText(notas.texto)
                    DateTime.text = notas.dateTime.toString()
                }
            }
        }

        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm")
        currentDate =sdf.format(Date())

        //Caixa de texto com data
        DateTime.text = currentDate

        //Button Guardar nota
        Save.setOnClickListener{
            if(notaId != -1){
                modificarNota()
            }else{
                guardarNota()
            }
        }

        //Button delete
       Delete.setOnClickListener{
           if(notaId != -1){
               apagarNota()
           }else{
               Toast.makeText(context, requireContext().getString(R.string.deleteNota), Toast.LENGTH_SHORT).show()
           }
       }

        Back.setOnClickListener{
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    //MODIFICAR NOTA
    private fun modificarNota(){
        launch {
            context?.let {
                var nota = NotasDB.getDatabase(it).noteDao().getSpecificNota(notaId)

                nota.titlo = Title.text.toString()
                nota.texto = Desc.text.toString()
                nota.dateTime = currentDate

                NotasDB.getDatabase(it).noteDao().updateNote(nota)
                Title.setText("")
                Desc.setText("")
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }

    //GUARDAR NOTA
    private fun guardarNota(){

        if (Title.text.isNullOrEmpty()){
            Toast.makeText(context, requireContext().getString(R.string.titleNota), Toast.LENGTH_SHORT).show()
        }
        else if (Desc.text.isNullOrEmpty()){

            Toast.makeText(context, requireContext().getString(R.string.descNota), Toast.LENGTH_SHORT).show()
        }
        else{
            launch {
                var nota = Notas()
                nota.titlo = Title.text.toString()
                nota.texto = Desc.text.toString()
                nota.dateTime = currentDate
                context?.let {
                    NotasDB.getDatabase(it).noteDao().insertNotas(nota)
                    Title.setText("")
                    Desc.setText("")
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }
        }
    }


    //APAGAR NOTA
    private fun apagarNota(){
        launch {
            context?.let {
                NotasDB.getDatabase(it).noteDao().deleteSpecificNota(notaId)
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }


    companion object {

        @JvmStatic
        fun newInstance(argnotaId : Int) =
            CriarLista().apply {
                arguments = Bundle().apply {
                    notaId = argnotaId
                }
            }
    }
}