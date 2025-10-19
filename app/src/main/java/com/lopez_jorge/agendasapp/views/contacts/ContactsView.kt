package com.lopez_jorge.agendasapp.views.contacts

import android.os.SystemClock.sleep
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.lopez_jorge.agendasapp.viewModels.ContactsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactsView(
    navController: NavController,
    contactVM: ContactsViewModel,
    onBack: () -> Unit = {}
) {
    // Al entrar a la vista, obtenemos los contactos
    LaunchedEffect(Unit) {
        contactVM.getContacts()
    }

    val isLoading by contactVM.isLoading
    val contacts = contactVM.contacts

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Contactos") },
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

        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            when {
                isLoading -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Cargando contactos…")
                    }
                }

                contacts.isEmpty() -> {
                    Text("No hay contactos disponibles.")
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        items(contacts) { contact ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                elevation = CardDefaults.cardElevation(4.dp)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(
                                        text = "👤 ${contact.name}",
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    TextButton(
                                        onClick = {
                                            contactVM.editingContact.value = contact   // 👈 guardamos el seleccionado
                                            navController.navigate("EditContact")      // 👈 navegamos
                                        }
                                    ) { Text("Editar") }
                                    Text(text = "✉️ ${contact.email}")
                                    if (contact.address.isNotBlank())
                                        Text(text = "🏡 ${contact.address}")
                                    if (contact.phone.isNotBlank())
                                        Text(text = "📞 ${contact.phone}")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
