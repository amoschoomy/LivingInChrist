package com.amoschoojs.livinginchrist

import android.content.SharedPreferences
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Chronometer
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

/**
 * Counter Checker Fragment
 */
class CounterFragment : Fragment() {
    private lateinit var arrayList: ArrayList<String>
    private var countStarted: Boolean = false
    private lateinit var sharedPreferences: SharedPreferences
    private val gson = Gson()
    private val arrayType: Type = object : TypeToken<ArrayList<String?>?>() {}.type


    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPreferences = activity?.getSharedPreferences("abc", 0)!!

        //populate arraylist from sharedPreferences if there is history added,
        // else just create new arraylist
        arrayList = if (!sharedPreferences.contains("history")) {
            val arrayString = gson.toJson(ArrayList<Int>())
            sharedPreferences.edit()?.putString("history", arrayString)
                ?.apply()
            gson.fromJson(
                sharedPreferences.getString("history", "[]"), arrayType
            )
        } else {
            val array = sharedPreferences.getString("history", "[]")
            gson.fromJson(array, arrayType)

        }

        super.onCreate(savedInstanceState)
        // get from sharedPreferences if user have an active count streak
        countStarted = sharedPreferences.getBoolean("countstatus", false)
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
        chronometerHandler(view) //handle chronometer view
    }


    /**
     * Handle chronometer view logic
     * @param view the chronometer text view
     */
    private fun chronometerHandler(view: View) {
        val duration = view.findViewById<Chronometer>(R.id.duration)
        val current = System.currentTimeMillis()

        // get time elapsed since user last exits and time stored
        val calc = SystemClock.elapsedRealtime() - (current - sharedPreferences.getLong(
            "startTime",
            current
        ))

        val resetButton = view.findViewById<Button>(R.id.reset)
        val startButton = view.findViewById<Button>(R.id.countstreak)

        //if count has started before, restart the count from where user left off
        if (countStarted) {
            duration.base = calc
            resetButton.isEnabled = true
            startButton.isEnabled = false
            duration.start()
        }

        // user start new count
        startButton.setOnClickListener {
            if (!countStarted) {
                duration.base = SystemClock.elapsedRealtime()
                duration.start()
                val start = System.currentTimeMillis()
                sharedPreferences.edit()?.putLong("startTime", start)?.apply()
                //put user starttime into shared
                //Preferences
                countStarted = true
                resetButton.isEnabled = true
                startButton.isEnabled = false
            }

        }

        // user decide to reset the count
        resetButton.setOnClickListener {
            duration.stop()
            arrayList.add(duration.text.toString())
            duration.base = SystemClock.elapsedRealtime()
            countStarted = false
            resetButton.isEnabled = false
            startButton.isEnabled = true
        }

    }


    override fun onPause() {
        val arrayString = gson.toJson(arrayList)
        val sharedPreferencesEditor = sharedPreferences.edit()


        // check if user deleted histroy when activity out of focus
        val deleted = sharedPreferences.getBoolean("clearedHistory", false)
        if (!deleted) {
            sharedPreferencesEditor?.putString("history", arrayString)
            sharedPreferencesEditor?.apply()
        } else {
            arrayList = ArrayList()
            sharedPreferencesEditor?.putBoolean("clearedHistory", false)?.apply()
        }
        super.onPause()
    }

    override fun onStop() {

        // user exit the app completely or OS destroyed, so save the current counter for it to be used later
        val duration = view?.findViewById<Chronometer>(R.id.duration)
        val sharedPreferencesEditor = activity?.getSharedPreferences("abc", 0)?.edit()
        sharedPreferencesEditor?.putBoolean("countstatus", countStarted)
            ?.apply() //saved whether an active count is running or not
        sharedPreferencesEditor?.putString("currentTime", duration?.text.toString())?.apply()
        super.onStop()
    }


}