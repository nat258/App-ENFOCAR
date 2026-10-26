package com.example.proyecto_esfocar.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.proyecto_esfocar.viewmodel.RegistroState
import com.example.proyecto_esfocar.viewmodel.RegistroViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import com.example.proyecto_esfocar.model.Usuario

// Función auxiliar para convertir los milisegundos del calendario a formato DD/MM/AAAA
fun formatearFechaMilisegundos(milis: Long): String {
    val formateador = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }
    return formateador.format(Date(milis))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    viewModel: RegistroViewModel,
    onRegistroExitoso: (Usuario) -> Unit, // <-- Pasa el objeto Usuario
    onVolverALogin: () -> Unit
) {
    val rut by viewModel.rut.collectAsState()
    val nombre by viewModel.nombre.collectAsState()
    val email by viewModel.email.collectAsState()
    val telefono by viewModel.telefono.collectAsState()
    val fechaNacimiento by viewModel.fechaNacimiento.collectAsState()
    val direccion by viewModel.direccion.collectAsState()
    val password by viewModel.password.collectAsState()
    val confirmarPassword by viewModel.confirmarPassword.collectAsState()
    val registroState by viewModel.registroState.collectAsState()

    // Estado para controlar la visibilidad del Calendario (DatePickerDialog)
    var mostrarDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    LaunchedEffect(registroState) {
        val estado = registroState
        if (estado is RegistroState.Success) {
            val usuarioCreado = estado.usuario
            viewModel.resetState()
            onRegistroExitoso(usuarioCreado)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Crear Cuenta Estudiante",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // 1. RUT
        OutlinedTextField(
            value = rut,
            onValueChange = { viewModel.rut.value = it },
            label = { Text("RUT (ej. 12345678-9)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 2. Nombre Completo
        OutlinedTextField(
            value = nombre,
            onValueChange = { viewModel.nombre.value = it },
            label = { Text("Nombre Completo") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 3. Correo Electrónico
        OutlinedTextField(
            value = email,
            onValueChange = { viewModel.email.value = it },
            label = { Text("Correo Electrónico") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 4. Teléfono
        OutlinedTextField(
            value = telefono,
            onValueChange = { viewModel.telefono.value = it },
            label = { Text("Teléfono") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 5. Fecha de Nacimiento con Selector de Calendario
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = fechaNacimiento,
                onValueChange = { },
                readOnly = true, // Hace que no se abra el teclado físico sino el diálogo
                label = { Text("Fecha de Nacimiento") },
                placeholder = { Text("DD/MM/AAAA") },
                trailingIcon = {
                    IconButton(onClick = { mostrarDatePicker = true }) {
                        Text("📅")
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            // Transparente encima para abrir el calendario al tocar cualquier parte del campo
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clickable { mostrarDatePicker = true }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 6. Dirección
        OutlinedTextField(
            value = direccion,
            onValueChange = { viewModel.direccion.value = it },
            label = { Text("Dirección") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 7. Contraseña
        OutlinedTextField(
            value = password,
            onValueChange = { viewModel.password.value = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 8. Confirmar Contraseña
        OutlinedTextField(
            value = confirmarPassword,
            onValueChange = { viewModel.confirmarPassword.value = it },
            label = { Text("Confirmar Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (registroState is RegistroState.Error) {
            Text(
                text = (registroState as RegistroState.Error).mensaje,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        Button(
            onClick = { viewModel.registrarUsuario() },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = registroState !is RegistroState.Loading
        ) {
            if (registroState is RegistroState.Loading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text("Registrarse")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onVolverALogin) {
            Text("¿Ya tienes cuenta? Inicia sesión")
        }

        // Diálogo Flotante del Calendario (DatePickerDialog)
        if (mostrarDatePicker) {
            DatePickerDialog(
                onDismissRequest = { mostrarDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        datePickerState.selectedDateMillis?.let { milis ->
                            viewModel.fechaNacimiento.value = formatearFechaMilisegundos(milis)
                        }
                        mostrarDatePicker = false
                    }) {
                        Text("Aceptar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { mostrarDatePicker = false }) {
                        Text("Cancelar")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
    }
}