package pt.atp.project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.fragment_ver_listas.*
import kotlinx.coroutines.launch
import pt.atp.project.persistence.Notas
import pt.atp.project.persistence.NotasDB
import pt.atp.project.rcnotas.RviewNotas
import java.util.Locale
import kotlin.collections.ArrayList


class VerListas : SubFragment() {

    var arrNotes = ArrayList<Notas>()
    var notasrc: RviewNotas = RviewNotas()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_ver_listas, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_view.setHasFixedSize(true)

        recycler_view.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)


        launch {
            context?.let{
                var notas = NotasDB.getDatabase(it).noteDao().getAllNotas()
                notasrc.setData(notas)
                arrNotes = notas as ArrayList<Notas>
                recycler_view.adapter = notasrc
            }
        }

        notasrc.setOnClickListener(onClicked)


        CreateNote.setOnClickListener {
            replaceFragment(CriarLista.newInstance(-1))
        }

        search.setOnQueryTextListener( object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                var tempArr = ArrayList<Notas>()

                for (arr in arrNotes){
                    if (arr.titlo!!.toLowerCase(Locale.getDefault()).contains(newText.toString().toLowerCase())){
                        tempArr.add(arr)
                    }
                }

                notasrc.setData(tempArr)
                notasrc.notifyDataSetChanged()
                return true
            }

        })

    }


    private val onClicked = object :RviewNotas.OnItemClickListener{
        override fun onClicked(notasId: Int) {


            var fragment :Fragment
            fragment = CriarLista.newInstance(notasId)

            replaceFragment(fragment)
        }

    }

    fun replaceFragment(fragment:Fragment){
        val fragmentTransition = requireActivity().supportFragmentManager.beginTransaction()

        fragmentTransition.replace(R.id.framelayout,fragment).addToBackStack(fragment.javaClass.simpleName).commit()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
                VerListas().apply {
                    arguments = Bundle().apply {
                    }
                }
    }
}