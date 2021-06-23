package com.amoschoojs.livinginchrist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CounterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CounterFragment : Fragment() {
    // TODO: Rename and change types of
    private lateinit var param1:ArrayList<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        if (!activity?.getSharedPreferences("abc",0)?.contains("history")!!){
            val gson = Gson()
            val arrayString=gson.toJson(ArrayList<Int>())
            val arrayType: Type =object: TypeToken<ArrayList<Int?>?>(){}.type
            activity?.getSharedPreferences("abc",0)?.edit()?.putString("history",arrayString)?.apply()
            param1=gson.fromJson<ArrayList<Int>>(
                activity?.getSharedPreferences("abc",0)!!.getString("history","[]"),arrayType)
        }
        else{
           val array= activity?.getSharedPreferences("abc",0)?.getString("history","[]")
            val arrayType: Type =object: TypeToken<ArrayList<Int?>?>(){}.type
            val gson:Gson=Gson()
            param1=gson.fromJson<ArrayList<Int>>(array,arrayType)

        }
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_counter2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)
        val streakButton:Button=view.findViewById(R.id.countstreak)
        val duration:Int=Integer.parseInt(view.findViewById<TextView>(R.id.duration).text.toString())
        streakButton.setOnClickListener {
                param1.add(duration)
        }
    }

    override fun onPause() {
        val gson = Gson()
        val arrayString=gson.toJson(param1)
        val sharedPreferencesEditor=activity?.getSharedPreferences("abc",0)?.edit()
        sharedPreferencesEditor?.putString("history",arrayString)
        sharedPreferencesEditor?.apply()
        super.onPause()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CounterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: ArrayList<Int>, param2: String) =
            CounterFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)
                }
            }
    }
}