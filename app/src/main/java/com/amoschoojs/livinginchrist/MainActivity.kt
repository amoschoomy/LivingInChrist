package com.amoschoojs.livinginchrist

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.text.HtmlCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.preference.PreferenceManager
import com.amoschoojs.livinginchrist.networkstream.NetworkRequestVerse
import com.amoschoojs.livinginchrist.networkstream.OnConnectionStatusChanged
import com.amoschoojs.livinginchrist.networkstream.VerseOfTheDay
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 * Main Activity of the app
 */
class MainActivity : AppCompatActivity() {
    private lateinit var mainDrawer: DrawerLayout
    private val CHANNEL_ID = "Reminder"
    private lateinit var votdTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nav_drawer)


        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        mainDrawer = findViewById(R.id.maindrawer)

        //set toolbar as activity action bar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val toggle = ActionBarDrawerToggle(
            this,
            mainDrawer,
            toolbar,
            R.string.open_menu,
            R.string.close_menu
        )
        mainDrawer.addDrawerListener(toggle)
        toggle.syncState()

        //set navigation view listener
        val navigationView = findViewById<NavigationView>(R.id.navview)
        navigationView.setNavigationItemSelectedListener(
            CustomNavigationViewListener(
                this,
                supportFragmentManager
            )
        )
        //listen to cardviews listeners
        cardViewListener()

        val sharedPreferences: SharedPreferences = getSharedPreferences("abc", 0)

        //if not exists user history, load a new arraylist instead
        if (!sharedPreferences.contains("history")) {
            val gson = Gson()
            val arrayString = gson.toJson(ArrayList<String>())
            sharedPreferences.edit().putString("history", arrayString).apply()
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        createNotificationChannel()

        //build a notifcation
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Reminder")
            .setContentText("Keep your faith and resist temptation")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setOngoing(true) //notifcation cannot be removed

        //notify user everytime they open app
        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(1, builder.build())
        }

        votdTextView = findViewById(R.id.bibleverse)


        /*
        Block below is to get user settings preferences and handle it
         */
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false)

        val sharedPreferencesPreferenceManager = PreferenceManager.getDefaultSharedPreferences(this)
        val votdPreference = sharedPreferencesPreferenceManager.getBoolean("votdpreference", true)


        NetworkRequestVerse.checkNetworkInfo(this, object : OnConnectionStatusChanged {
            override fun onChange(type: Boolean) {
                // if internet avalibale and user wants it shown, request from HTTP
                if (type && votdPreference) {
                    MainScope().launch {
                        withContext(Dispatchers.IO) {
                            val votd = NetworkRequestVerse.httpGet()
                            withContext(Dispatchers.Main) {
                                votdViewHandler(votd)

                            }
                        }
                    }
                } else if (!votdPreference) { //IF User dont want it, show this text instead
                    votdTextView.text = "Please activate Verse Of the Day Service in Settings"
                } else { //e;se get user last connected verse
                    val votdFromSP =
                        sharedPreferences.getString("votd", "Please connect to the Internet")
                    val html = Html.fromHtml(votdFromSP, HtmlCompat.FROM_HTML_MODE_COMPACT)
                    votdTextView.movementMethod = LinkMovementMethod.getInstance()
                    votdTextView.text = html


                }

            }

        })


    }

    /**
     * VerseOfTheDay TextView Handler
     * @param verseOfTheDay [VerseOfTheDay] object sent from HTTP
     */
    private fun votdViewHandler(verseOfTheDay: VerseOfTheDay) {
        val verse = verseOfTheDay.votd
        val content = verse.content
        val displayRef = verse.displayRef
        val permalink = verse.permalink
        val verseText =
            "<a href=\"$permalink\">\n$content\n <br>$displayRef </a>" //formatted according to HTML

        val html = Html.fromHtml(verseText, HtmlCompat.FROM_HTML_MODE_COMPACT) //convert into HTML
        votdTextView.text = html
        votdTextView.movementMethod = LinkMovementMethod.getInstance()
        getSharedPreferences("abc", 0).edit().putString("votd", verseText)
            .apply() //put inside sharedPreferences


    }

    /**
     * Create the NotificationChannel, but only on API 26+ because
     * the NotificationChannel class is new and not in the support library
     */
    private fun createNotificationChannel() {

        val channel = NotificationChannel(
            CHANNEL_ID,
            "Reminder",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Daily Reminder"
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    /**
     * Card Views listener
     */
    private fun cardViewListener() {
        /*
        Basically setOnClickListners handlers and start a new fragment accordingly
         */
        val counterCard: CardView = findViewById(R.id.countercard)
        val planCard: CardView = findViewById(R.id.plancard)
        val quizCard: CardView = findViewById(R.id.quizcard)
        val studyCard: CardView = findViewById(R.id.studycard)

        val fragmentManager: FragmentManager = supportFragmentManager
        counterCard.setOnClickListener {
            fragmentManager.beginTransaction().replace(R.id.frag1, CounterTimerFragment())
                .addToBackStack("counter").commit()
        }
        planCard.setOnClickListener {
            fragmentManager.beginTransaction().replace(R.id.frag1, PlanFragment())
                .addToBackStack("plans").commit()
        }
        quizCard.setOnClickListener {
            fragmentManager.beginTransaction().replace(R.id.frag1, QuizFragment())
                .addToBackStack("quizzes").commit()
        }
        studyCard.setOnClickListener {
            fragmentManager.beginTransaction().replace(R.id.frag1, BibleStudyFragment())
                .addToBackStack("bibstudy").commit()
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.gplayrating -> Toast.makeText(
                this,
                "Sorry not yet implemented",
                Toast.LENGTH_SHORT
            ).show()
            R.id.settings -> {
                val i = Intent(this, SettingsApp::class.java)
                startActivity(i)
            }
            R.id.usagetips -> MaterialAlertDialogBuilder(this).setTitle("Usage Tips")
                .setMessage(R.string.usage).setNeutralButton("Ok", null).show()
        }
        return true
    }

}
