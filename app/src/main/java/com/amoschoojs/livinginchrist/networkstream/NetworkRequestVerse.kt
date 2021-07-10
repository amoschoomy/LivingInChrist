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

        /**
         * Checks network info and handles connection and disconnection logic
         *
         * @param context Context of the application
         * @param onConnectionStatusChange Handles logic in the event of connection changes
         */
        fun checkNetworkInfo(
            context: Context,
            onConnectionStatusChange: OnConnectionStatusChanged
        ) {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

            if (capabilities == null) { //No internet connection
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


        /**
         * Make a request to HTTP protocol to the URL using coroutines
         * @return [VerseOfTheDay] object
         */
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

                // Parse json returned from input stream
                if (inputStream != null) {
                    val json = parseJSON(inputStream)
                    inputStream.close()
                    conn.disconnect()
                    json

                } else {
                    //else empty Verse object will be created and passed into VerseOfTheDay object
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


        /**
         * Function to parse JSON from InputStream
         * @param inputStream InputStream object from HTTP Get
         * @return [VerseOfTheDay]
         */
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

