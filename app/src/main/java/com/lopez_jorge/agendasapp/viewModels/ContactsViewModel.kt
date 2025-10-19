package com.lopez_jorge.agendasapp.viewModels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lopez_jorge.agendasapp.models.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

class ContactsViewModel : ViewModel() {

    // Lista observable de contactos
    val contacts = mutableStateListOf<Contact>()
    val isLoading = mutableStateOf(false)
    val editingContact = mutableStateOf<Contact?>(null)

    fun saveContact(contact: Contact, onResult: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                isLoading.value = true //empezamos la carga
                val url = URL("http://54.236.23.141/addContact.php")
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
            } finally {
                withContext(Dispatchers.Main){
                    isLoading.value = false
                }
            }
        }
    }

    // 📥 Método para obtener todos los contactos
    fun getContacts() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val url = URL("http://54.236.23.141/getContacts.php")
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "GET"
                conn.connectTimeout = 15000
                conn.readTimeout = 15000

                if (conn.responseCode == HttpURLConnection.HTTP_OK) {
                    val response = conn.inputStream.bufferedReader().readText()


                    val jsonArray = JSONArray(response) // si esto falla, el log anterior lo revelará

                    val tempList = mutableListOf<Contact>()
                    for (i in 0 until jsonArray.length()) {
                        val obj = jsonArray.getJSONObject(i)
                        tempList.add(
                            Contact(
                                id = obj.getInt("id"),
                                name = obj.getString("name"),
                                email = obj.getString("email"),
                                address = obj.optString("address"),
                                phone = obj.optString("phone")
                            )
                        )
                    }

                    kotlinx.coroutines.withContext(Dispatchers.Main) {
                        contacts.clear()
                        contacts.addAll(tempList)
                    }
                } else {
                    println("⚠️ Error HTTP: ${conn.responseCode}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateContact(contact: Contact, onResult: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val url = URL("http://54.236.23.141/updateContact.php")
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "POST"
                conn.doOutput = true
                conn.connectTimeout = 15000
                conn.readTimeout = 15000
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")

                val postData =
                    "id=${contact.id}" +
                            "&name=${java.net.URLEncoder.encode(contact.name, "UTF-8")}" +
                            "&email=${java.net.URLEncoder.encode(contact.email, "UTF-8")}" +
                            "&address=${java.net.URLEncoder.encode(contact.address, "UTF-8")}" +
                            "&phone=${java.net.URLEncoder.encode(contact.phone, "UTF-8")}"

                conn.outputStream.use { it.write(postData.toByteArray()) }

                val ok = if (conn.responseCode == HttpURLConnection.HTTP_OK) {
                    val response = conn.inputStream.bufferedReader().readText()
                    response.contains("\"success\":true")
                } else false

                withContext(Dispatchers.Main) { onResult(ok) }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) { onResult(false) }
            }
        }
    }

}
