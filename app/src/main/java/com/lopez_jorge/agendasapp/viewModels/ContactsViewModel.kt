package com.lopez_jorge.agendasapp.viewModels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lopez_jorge.agendasapp.models.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

class ContactsViewModel : ViewModel() {

    // Lista observable de contactos
    val contacts = mutableStateListOf<Contact>()

    fun saveContact(contact: Contact, onResult: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val url = URL("http://54.196.197.231/addContact.php")
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "POST"
                conn.doOutput = true
                conn.connectTimeout = 15000
                conn.readTimeout = 15000
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")

                val postData = "name=${java.net.URLEncoder.encode(contact.name, "UTF-8")}" +
                        "&email=${java.net.URLEncoder.encode(contact.email, "UTF-8")}" +
                        "&address=${java.net.URLEncoder.encode(contact.address, "UTF-8")}" +
                        "&phone=${java.net.URLEncoder.encode(contact.phone, "UTF-8")}"

                conn.outputStream.use { it.write(postData.toByteArray()) }

                val responseCode = conn.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val response = conn.inputStream.bufferedReader().readText()
                    onResult(response.contains("\"success\":true"))
                } else {
                    onResult(false)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                onResult(false)
            }
        }
    }

    // 📥 Método para obtener todos los contactos
    fun getContacts() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val url = URL("http://54.196.197.231/getContacts.php")
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "GET"
                conn.connectTimeout = 15000
                conn.readTimeout = 15000

                val responseCode = conn.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val response = conn.inputStream.bufferedReader().readText()
                    val jsonArray = JSONArray(response)

                    // Limpiamos la lista y agregamos lo nuevo
                    contacts.clear()
                    for (i in 0 until jsonArray.length()) {
                        val obj = jsonArray.getJSONObject(i)
                        val contact = Contact(
                            name = obj.getString("name"),
                            email = obj.getString("email"),
                            address = obj.optString("address"),
                            phone = obj.optString("phone")
                        )
                        contacts.add(contact)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
