package com.lopez_jorge.agendasapp.views.register

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.lopez_jorge.agendasapp.viewModels.LoginViewModel
import com.lopez_jorge.agendasapp.viewModels.RegisterViewModel

@Composable
fun RegisterView(navController: NavController, registerVM: RegisterViewModel){
    Text(text = "Registro de usuario")
}
