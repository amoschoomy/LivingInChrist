package com.amoschoojs.livinginchrist

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


/**
 * History Fragment
 */
class HistoryFragment : Fragment() {
    private lateinit var arrayList: ArrayList<String>
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private val arrayType: Type = object : TypeToken<ArrayList<String?>?>() {}.type
    private val gson = Gson()
    private lateinit var sharedPreferences:SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = activity?.getSharedPreferences("abc", 0)!!
        val deleteHistory: FloatingActionButton = view.findViewById(R.id.deletefab)

        //listener when user click the delete button
        deleteHistory.setOnClickListener {

            activity?.let { it1 ->
                MaterialAlertDialogBuilder(it1).setTitle("Delete Confirmation")
                    .setMessage("Are you sure you want to delete the history?")
                    .setPositiveButton("Yes")
                    { _, _ ->
                        run {
                            arrayList.clear()
                            val array = gson.toJson(arrayList)
                            //put in sharedPreferrences the new array
                            val editor = sharedPreferences.edit()?.putString("history", array)
                                ?.putBoolean("clearedHistory", true)
                            editor?.apply()
                            updateAdapter() //update adapter
                            Snackbar.make(view, "Deleted successfully", Snackbar.LENGTH_SHORT)
                                .show()
                        }
                    }.setNegativeButton("Cancel") { _, _ -> }.show()
            }

        }
    }

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        updateAdapter() //update adapter whenever menu becomes visible vice versa

    }

    /**
     * Function to update adapter in the layout
     */
    private fun updateAdapter() {
        val array = sharedPreferences.getString("history", "[]")
        arrayList = gson.fromJson(array, arrayType)
        val linearLayoutManager = LinearLayoutManager(requireContext())
        val recyclerView: RecyclerView? = view?.findViewById(R.id.recview)
        recyclerView?.layoutManager = linearLayoutManager
        recyclerView?.adapter = RecyclerViewAdapter(arrayList)
        recyclerViewAdapter = recyclerView?.adapter as RecyclerViewAdapter
        recyclerViewAdapter.notifyDataSetChanged()
    }


}