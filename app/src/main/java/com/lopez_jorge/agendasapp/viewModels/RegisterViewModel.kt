package com.lopez_jorge.agendasapp.viewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.URL
import java.net.URLEncoder
class RegisterViewModel : ViewModel() {

    suspend fun registerUser(username: String, email: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {

            val url = URL("http://54.236.23.141/registro.php")
            val postData = "usuario=${URLEncoder.encode(username, "UTF-8")}" +
                    "&email=${URLEncoder.encode(email, "UTF-8")}" +
                    "&password=${URLEncoder.encode(password, "UTF-8")}"

            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "POST"
            conn.doOutput = true
            conn.outputStream.write(postData.toByteArray(Charsets.UTF_8))

            val responseCode = conn.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val response = conn.inputStream.bufferedReader().readText()
                response.contains("\"success\":true")
            } else {
                false
            }
        }
    }
}
