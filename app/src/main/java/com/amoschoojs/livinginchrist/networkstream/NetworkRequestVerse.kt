package com.amoschoojs.livinginchrist.networkstream

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import com.google.gson.Gson
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class NetworkRequestVerse {


    companion object {
        fun checkNetworkInfo(
            context: Context,
            onConnectionStatusChange: OnConnectionStatusChanged
        ) {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities == null) {
                onConnectionStatusChange.onChange(false)
            }
            connectivityManager.registerDefaultNetworkCallback(object : NetworkCallback() {
                override fun onAvailable(network: Network) {
                    onConnectionStatusChange.onChange(true)
                }

                override fun onLost(network: Network) {
                    onConnectionStatusChange.onChange(false)
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    onConnectionStatusChange.onChange(false)
                }
            })
        }


        suspend fun httpGet(): VerseOfTheDay {

            return withContext(Dispatchers.IO) {
                val inputStream: InputStream


                // create URL
                val url = "http://www.biblegateway.com/votd/get/?format=json&version=NIV"
                val urlObject = URL(url)


                // create HttpURLConnection
                val conn: HttpURLConnection = urlObject.openConnection() as HttpURLConnection

                // make GET request to the given URL
                conn.connect()
                // receive response as inputStream
                inputStream = conn.inputStream


                if (inputStream != null) {
                    val json = parseJSON(inputStream)
                    inputStream.close()
                    conn.disconnect()
                    json

                } else {
                    val verse = Verse(
                        "", "", "", "", "", "", "",
                        "", "", "", "", "", "", ""
                    )
                    inputStream.close()
                    conn.disconnect()
                    VerseOfTheDay(verse)

                }

            }
        }


        private fun parseJSON(inputStream: InputStream): VerseOfTheDay {


            val inputStreamReader = InputStreamReader(inputStream, "UTF-8")
            val json = JsonParser.parseReader(inputStreamReader)
            inputStreamReader.close()
            val gson = Gson()
            inputStream.close()
            return gson.fromJson(json.toString(), VerseOfTheDay::class.java)
        }


    }
}

