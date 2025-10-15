package com.lopez_jorge.agendasapp.viewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.URL
import java.net.URLEncoder





class LoginViewModel : ViewModel() {

    suspend fun validateUser(email: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                // 🔸 Prueba con HTTP primero para desarrollo
                val url = URL("http://54.226.29.89/info.php")

                val postData = "usuario=${URLEncoder.encode(email, "UTF-8")}" +
                        "&password=${URLEncoder.encode(password, "UTF-8")}"

                val conn = url.openConnection() as HttpURLConnection
                conn.apply {
                    requestMethod = "POST"
                    doOutput = true
                    connectTimeout = 15000
                    readTimeout = 15000
                    setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
                    setRequestProperty("Charset", "utf-8")
                }

                // Escribir datos de forma segura
                conn.outputStream.use { os ->
                    os.write(postData.toByteArray(Charsets.UTF_8))
                }

                val responseCode = conn.responseCode
                println("✅ Código de respuesta HTTP: $responseCode")

                when (responseCode) {
                    HttpURLConnection.HTTP_OK -> {
                        val response = conn.inputStream.bufferedReader().use { it.readText() }
                        println("✅ Respuesta del servidor: $response")
                        response.contains("\"success\":true") || response.contains("success")
                    }
                    else -> {
                        val errorResponse = conn.errorStream?.bufferedReader()?.use { it.readText() }
                        println("❌ Error HTTP $responseCode: $errorResponse")
                        false
                    }
                }

            } catch (e: ConnectException) {
                println("❌ Error de conexión: El servidor no está disponible")
                false
            } catch (e: SocketTimeoutException) {
                println("❌ Timeout: El servidor no respondió a tiempo")
                false
            } catch (e: Exception) {
                println("❌ Error general: ${e.message}")
                e.printStackTrace()
                false
            }
        }
    }
}