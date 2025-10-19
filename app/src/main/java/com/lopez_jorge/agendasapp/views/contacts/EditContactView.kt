package com.lopez_jorge.agendasapp.views.contacts

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.lopez_jorge.agendasapp.viewModels.ContactsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditContactView(
    navController: NavController,
    contactsVM: ContactsViewModel,
    onBack: () -> Unit = {}
) {
    val original = contactsVM.editingContact.value
    // Si por alguna razón se entra sin contacto, volvemos atrás
    if (original == null) {
        LaunchedEffect(Unit) { onBack() }
        return
    }

    var name by remember { mutableStateOf(original.name) }
    var email by remember { mutableStateOf(original.email) }
    var address by remember { mutableStateOf(original.address) }
    var phone by remember { mutableStateOf(original.phone) }

    var saving by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar contacto") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    TextButton(
                        enabled = !saving,
                        onClick = {
                            if (name.isBlank() || email.isBlank()) {
                                error = "Nombre y email son obligatorios"
                                return@TextButton
                            }
                            saving = true
                            error = null
                            val toUpdate = original.copy(
                                name = name.trim(),
                                email = email.trim(),
                                address = address.trim(),
                                phone = phone.trim()
                            )
                            contactsVM.updateContact(toUpdate) { ok ->
                                saving = false
                                if (ok) {
                                    // Refrescamos la lista para ver cambios al volver
                                    contactsVM.getContacts()
                                    // Limpiamos selección y volvemos
                                    contactsVM.editingContact.value = null
                                    navController.popBackStack()
                                } else {
                                    error = "No se pudo guardar. Inténtalo de nuevo."
                                }
                            }
                        }
                    ) { Text("Guardar") }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            if (saving) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(12.dp))
            }

            OutlinedTextField(
                value = name, onValueChange = { name = it },
                label = { Text("Nombre") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = email, onValueChange = { email = it },
                label = { Text("Email") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = address, onValueChange = { address = it },
                label = { Text("Dirección") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = phone, onValueChange = { phone = it },
                label = { Text("Teléfono") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))

            if (error != null) {
                Text(error!!, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}

