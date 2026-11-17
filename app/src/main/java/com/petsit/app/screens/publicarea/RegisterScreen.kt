package com.petsit.app.screens.publicarea
import androidx.compose.runtime.Composable
@Composable fun RegisterScreen(onLogin: () -> Unit) {
    AuthContent(register = true, primaryAction = onLogin, secondaryAction = onLogin)
}

