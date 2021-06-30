package com.amoschoojs.livinginchrist

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class AlertDialogStudy {
    companion object{
        @JvmStatic
        fun showDialogStudy(context: Context,arrayList: ArrayList<String>) {
            val sharedPreferences=context.getSharedPreferences("abc",0)
            val sharedPreferencesEditor=context.getSharedPreferences("abc",0).edit()
            val linearLayout = LinearLayout(context)
            linearLayout.orientation = LinearLayout.VERTICAL
            val titleBox = EditText(context)
            titleBox.hint = "Title"
            linearLayout.addView(titleBox)
            val contentBox = EditText(context)
            contentBox.hint = "Content"
            linearLayout.addView(contentBox)
            val alertDialogBuilder=MaterialAlertDialogBuilder(context).setTitle("Add Topic").setView(linearLayout)
                .setPositiveButton("Submit",null)
                .setNegativeButton("Cancel") { _, _ -> }.show()

            val positiveButton=alertDialogBuilder.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setOnClickListener { if (sharedPreferences.contains(titleBox.text.toString())){
                Toast.makeText(context,"Found another similar title. Please put a different title",Toast.LENGTH_SHORT).show()
            }
            else{
                arrayList.add(titleBox.text.toString())
                sharedPreferencesEditor.putString(titleBox.text.toString(),contentBox.text.toString()).apply()
                alertDialogBuilder.dismiss()
            }
            }
        }
    }
}


class ThemeStudy : AppCompatActivity() {

    private lateinit var arrayPeace:ArrayList<String>
    private lateinit var arrayLove:ArrayList<String>
    private lateinit var arrayFaith:ArrayList<String>
    private lateinit var arrayKindness:ArrayList<String>
    private lateinit var arraySelfControl:ArrayList<String>
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor:SharedPreferences.Editor
    private lateinit var peaceAdapter:ArrayAdapter<String>
    private lateinit var loveAdapter:ArrayAdapter<String>
    private lateinit var kindnessAdapter:ArrayAdapter<String>
    private lateinit var faithAdapter:ArrayAdapter<String>
    private lateinit var selfControlAdapter:ArrayAdapter<String>
    private val arrayType: Type =object: TypeToken<ArrayList<String?>?>(){}.type
    private val gson= Gson()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_theme_study)
        val peaceSpinner=findViewById<Spinner>(R.id.peacespinner)
        val loveSpinner=findViewById<Spinner>(R.id.lovespinner)
        val faithSpinner=findViewById<Spinner>(R.id.faithfulnessspinner)
        val kindnessSpinner=findViewById<Spinner>(R.id.kindnessspinner)
        val selfControlSpinner=findViewById<Spinner>(R.id.selfcontrolspinner)
        sharedPreferences=getSharedPreferences("abc",0)
        editor=getSharedPreferences("abc",0).edit()
        var arrayString=sharedPreferences.getString("arraypeace","[]")
        arrayPeace=gson.fromJson(arrayString,arrayType)

        arrayString=sharedPreferences.getString("arraylove","[]")
        arrayLove=gson.fromJson(arrayString,arrayType)
        arrayString=sharedPreferences.getString("arrayfaith","[]")
        arrayFaith=gson.fromJson(arrayString,arrayType)
        arrayString=sharedPreferences.getString("arraykindness","[]")
        arrayKindness=gson.fromJson(arrayString,arrayType)
        arrayString=sharedPreferences.getString("arrayselfcontrol","[]")
        arraySelfControl=gson.fromJson(arrayString,arrayType)

        peaceAdapter=ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,arrayPeace)
        peaceSpinner.adapter=peaceAdapter

        loveAdapter=ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,arrayLove)
        loveSpinner.adapter=loveAdapter

        faithAdapter=ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,arrayFaith)
        faithSpinner.adapter=faithAdapter

        kindnessAdapter=ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,arrayKindness)
        kindnessSpinner.adapter=kindnessAdapter

        selfControlAdapter=ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,arraySelfControl)
        selfControlSpinner.adapter=selfControlAdapter


        addButtonsAction()



    }

    private fun addButtonsAction(){
        val addPeace=findViewById<FloatingActionButton>(R.id.addpeace)
        val addLove=findViewById<FloatingActionButton>(R.id.addlove)
        val addFaith=findViewById<FloatingActionButton>(R.id.addfaith)
        val addKindness=findViewById<FloatingActionButton>(R.id.addkindness)
        val addSelfControl=findViewById<FloatingActionButton>(R.id.addselfcontrol)


        addPeace.setOnClickListener {
            AlertDialogStudy.showDialogStudy(this,arrayPeace)
            peaceAdapter.notifyDataSetChanged()}
        addLove.setOnClickListener {
            AlertDialogStudy.showDialogStudy(this,arrayLove)
            loveAdapter.notifyDataSetChanged()}

        addFaith.setOnClickListener { AlertDialogStudy.showDialogStudy(this,arrayFaith)
        faithAdapter.notifyDataSetChanged()}

        addKindness.setOnClickListener { AlertDialogStudy.showDialogStudy(this,arrayKindness)
        kindnessAdapter.notifyDataSetChanged()}

        addSelfControl.setOnClickListener { AlertDialogStudy.showDialogStudy(this,arraySelfControl)
        selfControlAdapter.notifyDataSetChanged()}
    }
}