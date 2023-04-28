package com.example.dangillsa_kotlin

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class SendStatusTask {

    private val url = "http://211.227.224.194:5005/fallen"

    suspend fun sendStatus(status: String): String = withContext(Dispatchers.IO) {
        val result: String
        var conn: HttpURLConnection? = null // HttpURLConnection 객체를 null로 초기화

        try {
            conn = URL(url).openConnection() as HttpURLConnection
            conn.requestMethod = "POST"
            conn.doOutput = true
            conn.doInput = true

            val postData = "status=$status"
            conn.outputStream.write(postData.toByteArray(Charsets.UTF_8))
            conn.outputStream.flush()

            result = conn.inputStream.bufferedReader().use(BufferedReader::readText)
        } catch (e: Exception) {
            Log.e("SendStatusTask", "Exception: ${e.message}")
            throw e
        } finally {
            conn?.disconnect() // HttpURLConnection 객체를 종료
        }

        result
    }
}