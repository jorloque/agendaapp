package com.lopez_jorge.agendasapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import com.lopez_jorge.agendasapp.viewModels.LoginViewModel
import com.lopez_jorge.agendasapp.viewModels.NotasViewModel
import com.lopez_jorge.agendasapp.viewModels.RegisterViewModel
import com.lopez_jorge.agendasapp.views.login.LoginView
import com.lopez_jorge.agendasapp.views.register.RegisterView

@Composable
fun Navmanager(
    loginVM : LoginViewModel,
    registerVM: RegisterViewModel,
    notesVM: NotasViewModel
){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Login"){
        composable ("Login"){
            LoginView(navController,loginVM)
        }

        composable ("Register"){
            RegisterView(navController,registerVM)
        }
    }


}