package com.lopez_jorge.agendasapp.views.contacts

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import android.widget.Toast
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lopez_jorge.agendasapp.R
import com.lopez_jorge.agendasapp.models.Contact
import com.lopez_jorge.agendasapp.viewModels.ContactsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddContactsView(
    viewModel: ContactsViewModel = viewModel(),
    onBack: () -> Unit = {},
    onViewContacts: () -> Unit = {}
) {
    val context = LocalContext.current

    // Estados para los campos de texto
    var names by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Agregar contacto") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Imagen de contacto
            Image(
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)
                    .padding(20.dp),
                painter = painterResource(R.drawable.ico_agregar_contacto),
                contentDescription = "Agregar contacto"
            )

            // Nombre
            OutlinedTextField(
                value = names,
                onValueChange = { names = it },
                label = { Text(text = "Nombres") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp)
            )

            // Email
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp)
            )

            // Dirección
            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text(text = "Dirección") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp)
            )

            // Teléfono
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text(text = "Teléfono") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp)
            )


            // Botón para agregar contacto
            Button(
                onClick = {
                    if (names.isBlank() || email.isBlank()) {
                        Toast.makeText(context, "Nombre y Email son obligatorios", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    val contact = Contact(
                        name = names,
                        email = email,
                        address = address,
                        phone = phone
                    )

                    // 👉 Aquí llamamos al ViewModel
                    viewModel.saveContact(contact) { success ->
                        // Como saveContact se ejecuta en un hilo en background,
                        // volvemos al hilo principal con runOnUiThread (usando context de Compose)
                        android.os.Handler(android.os.Looper.getMainLooper()).post {
                            if (success) {
                                Toast.makeText(context, "Contacto agregado correctamente ✅", Toast.LENGTH_SHORT).show()
                                // Limpiamos los campos
                                names = ""
                                email = ""
                                address = ""
                                phone = ""
                            } else {
                                Toast.makeText(context, "Error al agregar contacto ❌", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp)
            ) {
                Text(text = "Agregar Contacto")
            }


            // Botón para ver lista de contactos
            Button(
                onClick = onViewContacts, // Navega a ListContact
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Text("Ver mis contactos")
            }
        }
    }
}
