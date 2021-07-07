package com.amoschoojs.livinginchrist.networkstream

import android.content.Context
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class NetworkRequestVerse {

    private val url:String="https://www.biblegateway.com/votd/get/?format=json&version=NIV"
    private fun isInternetAvailable(context: Context): Boolean {
        var result = false
        val networkCallback: NetworkCallback = object : NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
            }

            override fun onUnavailable() {
                super.onUnavailable()
            }
        }
        return result
    }

    private suspend fun httpGet(): VerseOfTheDay {

        val result = withContext(Dispatchers.IO) {
            val inputStream: InputStream


            // create URL
            val urlObject: URL = URL(url)

            // create HttpURLConnection
            val conn: HttpURLConnection = urlObject.openConnection() as HttpURLConnection

            // make GET request to the given URL
            conn.connect()

            // receive response as inputStream
            inputStream = conn.inputStream


            if (inputStream != null){
                parseJSON(inputStream)
            }
            else{
                val verse=Verse("","","","","","","",
                    "","","","","","","")
                VerseOfTheDay(verse)
        }

        }
        return result
    }

    private fun parseJSON(inputStream: InputStream): VerseOfTheDay {

        val bufferedReader: BufferedReader? = BufferedReader(InputStreamReader(inputStream))

        val gson = Gson()

        return gson.fromJson(bufferedReader, VerseOfTheDay::class.java)
    }

}