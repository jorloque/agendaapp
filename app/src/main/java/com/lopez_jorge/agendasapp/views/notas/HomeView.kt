package com.lopez_jorge.agendasapp.views.notas


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.lopez_jorge.agendasapp.utils.SessionManager
import com.lopez_jorge.agendasapp.viewModels.NotasViewModel
import com.lopez_jorge.agendasapp.R

@Composable
fun HomeView(navController: NavController, notesVM: NotasViewModel) {
    val context = LocalContext.current
    val sessionManager = SessionManager(context)
    val username = sessionManager.getUsername()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Bienvenido, $username",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 50.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Botón de cerrar sesión
        Button(
            onClick = {
                sessionManager.clearSession()  // ✅ borramos la sesión
                navController.navigate("Login") {
                    popUpTo("Home") { inclusive = true }  // ✅ limpiamos backstack
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
        ) {
            Text(text = "Cerrar Sesión")
        }

        //Imagen de contactos
        Image(
            modifier = Modifier
                .height(100.dp)
                .width(100.dp)
                .padding(20.dp),
            painter = painterResource(R.drawable.ico_contactos),
            contentDescription = ""
        )

        //Boton para agregar contacto
        Button(
            onClick = {
                navController.navigate("AddContact")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end=20.dp)
        ) {
            Text(text = "Agregar contacto")
        }

        //Boton para ver contactos
        Button(
            onClick = {
                navController.navigate("ListContact")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end=20.dp)
        ) {
            Text(text = "Visualizar contactos")
        }
    }
}
