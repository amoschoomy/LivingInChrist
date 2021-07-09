package com.amoschoojs.livinginchrist

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.time.Instant
import java.time.ZoneId


class PlanFragment : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var completionVal: TextView
    private lateinit var qt1: CheckBox
    private lateinit var w1: CheckBox
    private lateinit var bibleStudy: CheckBox
    private lateinit var w2: CheckBox
    private lateinit var qt2: CheckBox
    private lateinit var submitButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = activity?.getSharedPreferences("abc", 0)!!
        editor = sharedPreferences.edit()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        qt1 = view.findViewById(R.id.quiettime)
        w1 = view.findViewById(R.id.worship)
        bibleStudy = view.findViewById(R.id.biblestudy)
        w2 = view.findViewById(R.id.worship2)
        qt2 = view.findViewById(R.id.quiettime2)
        submitButton = view.findViewById(R.id.submit)
        completionVal = view.findViewById(R.id.completeval)


        val number = sharedPreferences.getInt("number", 0)
        completionVal.text = "Completed " + (number).toString() + " times"
        val showPlanMessage: Boolean = sharedPreferences.getBoolean("showplanmessage", true)
        if (showPlanMessage) {
            activity?.let {
                MaterialAlertDialogBuilder(it).setTitle("How to Use")
                    .setMessage(R.string.popup_plan)
                    .setNegativeButton("OK and DON'T SHOW AGAIN") { _, _ ->
                        editor.putBoolean("showplanmessage", false).apply()
                    }.setPositiveButton("OK") { _, _ -> }.show()
            }


        }
        restoreCheckboxes()
        checkBoxLogic()
    }

    private fun checkBoxLogic() {
        val zoneId = ZoneId.systemDefault()

        val timeNow = Instant.now().atZone(zoneId)
        val day = sharedPreferences.getInt("daycomplete", timeNow.dayOfMonth)
        val month = sharedPreferences.getInt("monthcomplete", timeNow.monthValue)
        if ((timeNow.dayOfMonth > day || timeNow.monthValue > month)) {
            unCheckCheckBoxes()
            disableCheckboxes(false)
            editor.putBoolean("submitted", false).apply()
        }
        submitButton.setOnClickListener {
            val submittedDay = sharedPreferences.getBoolean("submitted", false)
            if (qt2.isChecked && !submittedDay) {
                activity?.let {
                    MaterialAlertDialogBuilder(it).setTitle("Submit Confirmation")
                        .setMessage("Have you completed the activities and want to submit?")
                        .setNegativeButton("No, I have not completed yet") { _, _ ->
                        }.setPositiveButton("Yes")
                        { _, _ ->
                            disableCheckboxes(true)
                            val number =
                                completionVal.text.toString().replace(Regex("[^0-9]"), "").toInt()
                            editor.putInt("number", number + 1)
                            completionVal.text = "Completed " + (number + 1).toString() + " times"
                            sharedPreferences.edit().putInt("daycomplete", timeNow.dayOfMonth)
                            editor.putInt("monthcomplete", timeNow.monthValue)
                            editor.putBoolean("submitted", true)
                            editor.apply()
                            Toast.makeText(activity, "Submitted successfully", Toast.LENGTH_SHORT)
                                .show()
                        }.show()
                }

            }
        }

        qt1.setOnCheckedChangeListener { compoundButton, _ ->
            if (compoundButton.isChecked) {
                w1.isEnabled = true
                editor.putBoolean("w1Enabled", w1.isEnabled)
                editor.putBoolean("qt1Checked", qt1.isChecked)
                editor.apply()
            }
        }

        w1.setOnCheckedChangeListener { compoundButton, _ ->
            if (compoundButton.isChecked) {
                bibleStudy.isEnabled = true
                editor.putBoolean("bibleStudyEnabled", bibleStudy.isEnabled)
                editor.putBoolean("w1Checked", w1.isChecked)
                editor.apply()

            }
        }

        bibleStudy.setOnCheckedChangeListener { compoundButton, _ ->
            if (compoundButton.isChecked) {
                w2.isEnabled = true
                editor.putBoolean("w2Enabled", w2.isEnabled)
                editor.putBoolean("bibleStudyChecked", bibleStudy.isChecked)
                editor.apply()

            }
        }

        w2.setOnCheckedChangeListener { compoundButton, _ ->
            if (compoundButton.isChecked) {
                qt2.isEnabled = true
                editor.putBoolean("qt2Enabled", qt2.isEnabled)
                editor.putBoolean("w2Checked", w2.isChecked)
                editor.apply()
            }
        }

        qt2.setOnCheckedChangeListener { compoundButton, _ ->
            if (compoundButton.isChecked) {
                editor.putBoolean("qt2Checked", qt2.isChecked)
                editor.apply()
            }
        }


    }

    private fun unCheckCheckBoxes() {


        qt1.isChecked = false

        w1.isChecked = false
        bibleStudy.isChecked = false
        w2.isChecked = false
        qt2.isChecked = false
        editor.putBoolean("qt1Checked", qt1.isChecked)
        editor.putBoolean("w1Checked", w1.isChecked)
        editor.putBoolean("bibleStudyChecked", bibleStudy.isChecked)
        editor.putBoolean("w2Checked", w2.isChecked)
        editor.putBoolean("qt2Checked", qt2.isChecked)
        editor.apply()
    }

    private fun disableCheckboxes(status: Boolean) {


        if (status) {
            qt1.isEnabled = false
            editor.putBoolean("qt1Enabled", qt1.isEnabled)
        } else {
            qt1.isEnabled = true
            editor.putBoolean("qt1Enabled", qt1.isEnabled)
        }

        w1.isEnabled = false
        bibleStudy.isEnabled = false
        w2.isEnabled = false
        qt2.isEnabled = false
        editor.putBoolean("w1Enabled", w1.isEnabled)
        editor.putBoolean("bibleStudyEnabled", bibleStudy.isEnabled)
        editor.putBoolean("w2Enabled", w2.isEnabled)
        editor.putBoolean("qt2Enabled", qt2.isEnabled)
        editor.apply()


    }

    private fun restoreCheckboxes() {

        qt1.isChecked = sharedPreferences.getBoolean("qt1Checked", false)
        w1.isChecked = sharedPreferences.getBoolean("w1Checked", false)
        bibleStudy.isChecked = sharedPreferences.getBoolean("bibleStudyChecked", false)
        w2.isChecked = sharedPreferences.getBoolean("w2Checked", false)
        qt2.isChecked = sharedPreferences.getBoolean("qt2Checked", false)

        qt1.isEnabled = sharedPreferences.getBoolean("qt1Enabled", true)
        w1.isEnabled = sharedPreferences.getBoolean("w1Enabled", false)
        bibleStudy.isEnabled = sharedPreferences.getBoolean("bibleStudyEnabled", false)
        w2.isEnabled = sharedPreferences.getBoolean("w2Enabled", false)
        qt2.isEnabled = sharedPreferences.getBoolean("qt2Enabled", false)

    }


}