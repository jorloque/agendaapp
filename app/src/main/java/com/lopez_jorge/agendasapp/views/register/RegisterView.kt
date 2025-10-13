package com.lopez_jorge.agendasapp.views.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.lopez_jorge.agendasapp.R
import com.lopez_jorge.agendasapp.viewModels.LoginViewModel
import com.lopez_jorge.agendasapp.viewModels.RegisterViewModel
import java.nio.file.WatchEvent

@Composable
fun RegisterView(navController: NavController, registerVM: RegisterViewModel){
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ){
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var username by remember { mutableStateOf("") }

        Text(
            modifier = Modifier.padding(top = 30.dp),
            text = "Registro de usuario",
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            fontSize = 20.sp
        )

        Image(
            modifier = Modifier
                .height(150.dp)
                .width(150.dp)
                .padding(15.dp),
            painter = painterResource(R.drawable.registro),
            contentDescription = "Logo de registro"
        )

        OutlinedTextField(
            value = username,
            onValueChange = {username = it},
            label = {Text(text="Username")},
            modifier = Modifier
                .fillMaxWidth()
                .padding(start=30.dp, end = 30.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = {email = it},
            label = {Text(text="Email")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start=30.dp, end = 30.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = {password = it},
            label = {Text(text="Password")},
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start=30.dp, end = 30.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {/*TODO*/},
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp,end= 30.dp)) {
            Text(text = "Registrar")
        }
    }
}
