package com.amoschoojs.livinginchrist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HistoryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: ArrayList<Int>? = null
    private var param2: String? = null
    private lateinit var arrayList:ArrayList<Int>
    private lateinit var recyclerViewAdapter:RecyclerViewAdapter
    private val arrayType: Type =object: TypeToken<ArrayList<Int?>?>(){}.type
    private val gson=Gson()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateAdapter()

    }

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        updateAdapter()

    }

    private fun updateAdapter(){
        val sharedPreferences=activity?.getSharedPreferences("abc",0)
        val array=sharedPreferences?.getString("history","[]")
        arrayList=gson.fromJson(array,arrayType)
        val linearLayoutManager=LinearLayoutManager(requireContext())
        val recyclerView:RecyclerView?=view?.findViewById(R.id.recview)
        recyclerView?.layoutManager=linearLayoutManager
        recyclerView?.adapter= RecyclerViewAdapter(arrayList)
        recyclerViewAdapter= recyclerView?.adapter as RecyclerViewAdapter
        recyclerViewAdapter.notifyDataSetChanged()
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HistoryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: ArrayList<Int>, param2: String) =
            HistoryFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}