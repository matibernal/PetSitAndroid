package com.petsit.app.screens.publicarea
import androidx.compose.runtime.Composable
@Composable fun LoginScreen(
    onLogin: () -> Unit,
    onRegister: () -> Unit,
    onRecovery: () -> Unit,
    onShelterLogin: () -> Unit
) {
    AuthContent(
        register = false,
        primaryAction = onLogin,
        secondaryAction = onRegister,
        recoveryAction = onRecovery,
        shelterLoginAction = onShelterLogin
    )
}
