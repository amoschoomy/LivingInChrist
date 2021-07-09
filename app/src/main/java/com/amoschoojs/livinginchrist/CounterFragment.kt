package com.amoschoojs.livinginchrist

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Chronometer
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

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
    private lateinit var param1: ArrayList<String>
    private var countStarted:Boolean=false


    override fun onCreate(savedInstanceState: Bundle?) {
        if (!activity?.getSharedPreferences("abc",0)?.contains("history")!!){
            val gson = Gson()
            val arrayString=gson.toJson(ArrayList<Int>())
            val arrayType: Type =object: TypeToken<ArrayList<String?>?>(){}.type
            activity?.getSharedPreferences("abc",0)?.edit()?.putString("history",arrayString)?.apply()
            param1=gson.fromJson<ArrayList<String>>(
                activity?.getSharedPreferences("abc",0)!!.getString("history","[]"),arrayType)
        }
        else{
           val array= activity?.getSharedPreferences("abc",0)?.getString("history","[]")
            val arrayType: Type =object: TypeToken<ArrayList<String?>?>(){}.type
            val gson=Gson()
            param1=gson.fromJson(array,arrayType)

        }

        super.onCreate(savedInstanceState)

        val sharedPreferences=requireActivity().getSharedPreferences("abc",0)
        countStarted=sharedPreferences.getBoolean("countstatus",false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_countertimer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        chronometerHandler(view)
    }


    private fun chronometerHandler(view: View){
        val duration=view.findViewById<Chronometer>(R.id.duration)
        val sharedPreferences=activity?.getSharedPreferences("abc",0)
        val current=System.currentTimeMillis()
        val calc=SystemClock.elapsedRealtime()-(current- sharedPreferences?.getLong("startTime",current)!!)

        val resetButton=view.findViewById<Button>(R.id.reset)
        val startButton=view.findViewById<Button>(R.id.countstreak)
        if(countStarted){
            duration.base=calc
            resetButton.isEnabled=true
            startButton.isEnabled=false
            duration.start()
        }

        startButton.setOnClickListener {
            Log.e("TEST","clicked start button")
            if(!countStarted){
                duration.base=SystemClock.elapsedRealtime()
                duration.start()
                val start=System.currentTimeMillis()
                sharedPreferences.edit()?.putLong("startTime",start)?.apply()
                countStarted=true
                resetButton.isEnabled=true
                startButton.isEnabled=false
            }

        }

        resetButton.setOnClickListener {
            duration.stop()
            param1.add(duration.text.toString())
            duration.base=SystemClock.elapsedRealtime()
            countStarted=false
            resetButton.isEnabled=false
            startButton.isEnabled=true
        }

    }


    override fun onPause() {
        val gson = Gson()
        val arrayString=gson.toJson(param1)
        val sharedPreferences=activity?.getSharedPreferences("abc",0)
        val sharedPreferencesEditor=sharedPreferences?.edit()
        var deleted=sharedPreferences?.getBoolean("clearedHistory",false)
        if(!deleted!!){
        sharedPreferencesEditor?.putString("history",arrayString)
        sharedPreferencesEditor?.apply()
        }
        else{
            param1=ArrayList()
            sharedPreferencesEditor?.putBoolean("clearedHistory",false)?.apply()
        }
        super.onPause()
    }

    override fun onStop() {
        val duration=view?.findViewById<Chronometer>(R.id.duration)
        val sharedPreferencesEditor=activity?.getSharedPreferences("abc",0)?.edit()
        sharedPreferencesEditor?.putBoolean("countstatus",countStarted)?.apply()
        sharedPreferencesEditor?.putString("currentTime",duration?.text.toString())?.apply()
        super.onStop()
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