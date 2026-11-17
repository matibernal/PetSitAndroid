package com.petsit.app

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import ar.edu.davinci.petsit.mobile.R
import com.petsit.app.ui.components.PetSitPage
import com.petsit.app.ui.theme.PetSitTheme

fun interface SubmitLogin {
    fun submit(email: String, password: String)
}

class LoginView {
    var loading by mutableStateOf(false)

    fun show(activity: ComponentActivity, submit: SubmitLogin, register: Runnable, preview: Runnable) {
        activity.setContent {
            PetSitTheme {
                var email by rememberSaveable { mutableStateOf("") }
                var password by remember { mutableStateOf("") }
                PetSitPage {
                    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                        Column(
                            Modifier.fillMaxWidth().padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Image(painterResource(R.drawable.petsit_login_logo), "PetSit", Modifier.height(90.dp))
                            Text("Bienvenido de nuevo", style = MaterialTheme.typography.headlineSmall)
                            OutlinedTextField(email, { email = it }, label = { Text("Correo electrónico") },
                                singleLine = true, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email))
                            OutlinedTextField(password, { password = it }, label = { Text("Contraseña") },
                                singleLine = true, visualTransformation = PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password))
                            Button(onClick = { submit.submit(email, password) }, enabled = !loading) {
                                Text(if (loading) "Ingresando…" else "Iniciar sesión")
                            }
                            TextButton(onClick = register::run, enabled = !loading) { Text("Crear cuenta") }
                            TextButton(onClick = preview::run, enabled = !loading) { Text("Explorar vistas de demostración") }
                        }
                    }
                }
            }
        }
    }
}
