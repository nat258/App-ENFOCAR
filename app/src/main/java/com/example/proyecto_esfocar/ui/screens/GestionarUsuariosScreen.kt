package com.example.proyecto_esfocar.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import com.example.proyecto_esfocar.model.Usuario
import com.example.proyecto_esfocar.ui.components.EsfocarTopBar
import com.example.proyecto_esfocar.data.repository.UsuarioRepository

@Composable
fun GestionarUsuariosScreen(
    usuarioRepository: UsuarioRepository,
    onVolver: () -> Unit
) {
    var usuarios by remember { mutableStateOf<List<Usuario>>(emptyList()) }
    var usuarioAEditar by remember { mutableStateOf<Usuario?>(null) }
    var usuarioAEliminar by remember { mutableStateOf<Usuario?>(null) }
    var mensaje by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    suspend fun cargarUsuarios() {
        usuarios = usuarioRepository.obtenerTodosLosUsuarios()
    }

    LaunchedEffect(Unit) {
        cargarUsuarios()
    }

    Scaffold(
        topBar = { EsfocarTopBar("Gestionar usuarios") }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Usuarios registrados: ${usuarios.size}",
                style = MaterialTheme.typography.titleMedium
            )

            mensaje?.let {
                Text(text = it, color = MaterialTheme.colorScheme.primary)
            }

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(usuarios, key = { it.id }) { usuario ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = usuario.nombre,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(text = usuario.email)
                            Text(text = "Rol: ${usuario.rol}")

                            if (usuario.rol != "Administrador") {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    OutlinedButton(
                                        onClick = {
                                            usuarioAEditar = usuario
                                        },
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text("Editar")
                                    }

                                    Button(
                                        onClick = {
                                            usuarioAEliminar = usuario
                                        },
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text("Eliminar")
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Button(
                onClick = onVolver,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver")
            }
        }
    }

    usuarioAEditar?.let { usuario ->
        var nombre by remember(usuario.id) { mutableStateOf(usuario.nombre) }
        var email by remember(usuario.id) { mutableStateOf(usuario.email) }
        var rol by remember(usuario.id) { mutableStateOf(usuario.rol) }

        AlertDialog(
            onDismissRequest = { usuarioAEditar = null },
            title = { Text("Editar usuario") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = { Text("Nombre") },
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Correo electrónico") },
                        singleLine = true
                    )
                    Text("Rol actual: $rol")
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedButton(
                            onClick = { rol = "Estudiante" },
                            enabled = rol != "Estudiante"
                        ) {
                            Text("Estudiante")
                        }
                        OutlinedButton(
                            onClick = { rol = "Profesor" },
                            enabled = rol != "Profesor"
                        ) {
                            Text("Profesor")
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val nombreLimpio = nombre.trim()
                        val emailLimpio = email.trim()

                        if (nombreLimpio.isBlank() || emailLimpio.isBlank()) {
                            mensaje = "El nombre y el correo son obligatorios."
                        } else {
                            scope.launch {
                                val usuarioActualizado = usuario.copy(
                                    nombre = nombreLimpio,
                                    email = emailLimpio,
                                    rol = rol
                                )
                                val resultado =
                                    usuarioRepository.actualizarUsuario(usuarioActualizado)

                                if (resultado > 0) {
                                    cargarUsuarios()
                                    mensaje = "Usuario actualizado correctamente."
                                    usuarioAEditar = null
                                } else {
                                    mensaje = "No se pudo actualizar el usuario."
                                }
                            }
                        }
                    }
                ) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                TextButton(onClick = { usuarioAEditar = null }) {
                    Text("Cancelar")
                }
            }
        )
    }

    usuarioAEliminar?.let { usuario ->
        AlertDialog(
            onDismissRequest = { usuarioAEliminar = null },
            title = { Text("Eliminar usuario") },
            text = {
                Text("¿Seguro que deseas eliminar la cuenta de ${usuario.nombre}? Esta acción no se puede deshacer.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        scope.launch {
                            val resultado = usuarioRepository.eliminarUsuario(usuario)
                            if (resultado > 0) {
                                cargarUsuarios()
                                mensaje = "Usuario eliminado correctamente."
                            } else {
                                mensaje = "No se pudo eliminar el usuario."
                            }
                            usuarioAEliminar = null
                        }
                    }
                ) {
                    Text("Sí, eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = { usuarioAEliminar = null }) {
                    Text("Cancelar")
                }
            }
        )
    }
}