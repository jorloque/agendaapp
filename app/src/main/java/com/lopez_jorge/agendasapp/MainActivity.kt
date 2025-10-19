package com.lopez_jorge.agendasapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.content.TransferableContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.lopez_jorge.agendasapp.navigation.Navmanager
import com.lopez_jorge.agendasapp.ui.theme.AgendasappTheme
import com.lopez_jorge.agendasapp.viewModels.ContactsViewModel
import com.lopez_jorge.agendasapp.viewModels.LoginViewModel
import com.lopez_jorge.agendasapp.viewModels.NotasViewModel
import com.lopez_jorge.agendasapp.viewModels.RegisterViewModel
import com.lopez_jorge.agendasapp.views.login.LoginView

class MainActivity : ComponentActivity() {

    val loginVM: LoginViewModel by viewModels()
    val notasVM: NotasViewModel by viewModels()
    val registerVM: RegisterViewModel by viewModels()
    val contactsVM : ContactsViewModel by viewModels ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AgendasappTheme {
                Surface (modifier = Modifier.fillMaxSize()) {
                    //LoginView()
                    Navmanager(loginVM, registerVM, notasVM, contactsVM)
                }
            }
        }
    }
}

