package com.lopez_jorge.agendasapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import com.lopez_jorge.agendasapp.viewModels.LoginViewModel
import com.lopez_jorge.agendasapp.viewModels.NotasViewModel
import com.lopez_jorge.agendasapp.viewModels.RegisterViewModel
import com.lopez_jorge.agendasapp.views.login.LoginView
import com.lopez_jorge.agendasapp.views.notas.HomeView
import com.lopez_jorge.agendasapp.views.register.RegisterView

@Composable
fun Navmanager(
    loginVM : LoginViewModel,
    registerVM: RegisterViewModel,
    notesVM: NotasViewModel
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

        composable ("Home"){
            HomeView(navController,notesVM)
        }
    }


}