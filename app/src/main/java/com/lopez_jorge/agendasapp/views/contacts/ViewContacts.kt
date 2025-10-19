package com.lopez_jorge.agendasapp.views.contacts

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lopez_jorge.agendasapp.viewModels.ContactsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewContactsView(
    viewModel: ContactsViewModel = viewModel(),
    onBack: () -> Unit = {}
) {
    // Al entrar a la vista, obtenemos los contactos
    LaunchedEffect(Unit) {
        viewModel.getContacts()
    }

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
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            items(viewModel.contacts) { contact ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "👤 ${contact.name}", style = MaterialTheme.typography.titleMedium)
                        Text(text = "✉️ ${contact.email}")
                        if (contact.address.isNotBlank()) Text(text = "🏡 ${contact.address}")
                        if (contact.phone.isNotBlank()) Text(text = "📞 ${contact.phone}")
                    }
                }
            }
        }
    }
}
