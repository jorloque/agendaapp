package com.lopez_jorge.agendasapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import com.lopez_jorge.agendasapp.viewModels.ContactsViewModel
import com.lopez_jorge.agendasapp.viewModels.LoginViewModel
import com.lopez_jorge.agendasapp.viewModels.NotasViewModel
import com.lopez_jorge.agendasapp.viewModels.RegisterViewModel
import com.lopez_jorge.agendasapp.views.contacts.AddContactsView
import com.lopez_jorge.agendasapp.views.contacts.ContactsView
import com.lopez_jorge.agendasapp.views.contacts.EditContactView
import com.lopez_jorge.agendasapp.views.login.LoginView
import com.lopez_jorge.agendasapp.views.notas.HomeView
import com.lopez_jorge.agendasapp.views.register.RegisterView

@Composable
fun Navmanager(
    loginVM : LoginViewModel,
    registerVM: RegisterViewModel,
    notesVM: NotasViewModel,
    contactsVM : ContactsViewModel

){
    val navController = rememberNavController()

    val context = LocalContext.current
    val sessionManager = remember { com.lopez_jorge.agendasapp.utils.SessionManager(context) }

    val startDestination = if (sessionManager.isLoggedIn()) "Home" else "Login"


    NavHost(navController = navController, startDestination = startDestination){
        composable ("Login"){
            LoginView(navController,loginVM)
        }

        composable ("Register"){
            RegisterView(navController,registerVM)
        }
        //se modifica esta ruta
        composable ("Home"){
            HomeView(navController,notesVM)
        }

        composable("AddContact") {
            AddContactsView(navController,contactsVM,
                // Pasamos lambda para navegar a la lista de contactos
                onViewContacts = { navController.navigate("ListContact") } ,
                // Botón Back regresa a la pantalla anterior
                onBack = { navController.popBackStack() }
            )
        }

        composable("ListContact") {
            ContactsView(navController,contactsVM,
                // Botón Back regresa a la pantalla anterior
                onBack = { navController.popBackStack() }
            )
        }

        composable("EditContact") {
            EditContactView(
                navController = navController,
                contactsVM = contactsVM,
                onBack = { navController.popBackStack() }
            )
        }

    }


}