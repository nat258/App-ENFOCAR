package com.example.proyecto_esfocar.ui.screens

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.proyecto_esfocar.R
import com.example.proyecto_esfocar.model.Usuario
import com.example.proyecto_esfocar.ui.components.EsfocarTopBar
import com.example.proyecto_esfocar.viewmodel.PerfilViewModel
import java.io.File
import java.io.FileOutputStream

// Guarda el Bitmap tomado con la cámara en la carpeta de cache temporal del teléfono
fun guardarBitmapEnCache(context: Context, bitmap: Bitmap): String {
    val archivo = File(context.cacheDir, "foto_perfil_${System.currentTimeMillis()}.jpg")
    FileOutputStream(archivo).use { out ->
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
    }
    return Uri.fromFile(archivo).toString()
}

@Composable
fun PerfilScreen(
    usuario: Usuario,
    viewModel: PerfilViewModel,
    onVolver: () -> Unit
) {
    val context = LocalContext.current

    val nombre by viewModel.nombre.collectAsState()
    val email by viewModel.email.collectAsState()
    val telefono by viewModel.telefono.collectAsState()
    val rut by viewModel.rut.collectAsState()
    val fechaNacimiento by viewModel.fechaNacimiento.collectAsState()
    val direccion by viewModel.direccion.collectAsState()
    val fotoPerfil by viewModel.fotoPerfil.collectAsState()
    val mensaje by viewModel.mensaje.collectAsState()
    val guardando by viewModel.guardando.collectAsState()

    var mostrarOpcionesFoto by remember { mutableStateOf(false) }

    // Lanzador para la Galería
    val galeriaLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { viewModel.cambiarFotoPerfil(it.toString()) }
    }

    // Lanzador para la Cámara
    val camaraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        bitmap?.let {
            val uriString = guardarBitmapEnCache(context, it)
            viewModel.cambiarFotoPerfil(uriString)
        }
    }

    LaunchedEffect(usuario.id) {
        viewModel.cargarUsuario(usuario)
    }

    Scaffold(
        topBar = { EsfocarTopBar("Mi perfil") }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Mis datos personales",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Avatar circular interactivo con la foto de perfil
            Box(
                contentAlignment = Alignment.BottomEnd,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                AsyncImage(
                    model = if (fotoPerfil.isNotBlank()) fotoPerfil else R.drawable.escudo_esfocar_app,
                    contentDescription = "Foto de Perfil",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .border(3.dp, MaterialTheme.colorScheme.primary, CircleShape)
                        .clickable { mostrarOpcionesFoto = true },
                    contentScale = ContentScale.Crop
                )

                FloatingActionButton(
                    onClick = { mostrarOpcionesFoto = true },
                    modifier = Modifier.size(36.dp),
                    shape = CircleShape,
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Text("📷")
                }
            }

            TextButton(onClick = { mostrarOpcionesFoto = true }) {
                Text("Cambiar foto de perfil")
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = nombre,
                onValueChange = viewModel::cambiarNombre,
                label = { Text("Nombre completo") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                enabled = !guardando
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = email,
                onValueChange = viewModel::cambiarEmail,
                label = { Text("Correo electrónico") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                enabled = !guardando
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = telefono,
                onValueChange = viewModel::cambiarTelefono,
                label = { Text("Teléfono") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                enabled = !guardando
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = rut,
                onValueChange = viewModel::cambiarRut,
                label = { Text("RUT") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                enabled = !guardando
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = fechaNacimiento,
                onValueChange = viewModel::cambiarFechaNacimiento,
                label = { Text("Fecha de nacimiento") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                enabled = !guardando
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = direccion,
                onValueChange = viewModel::cambiarDireccion,
                label = { Text("Dirección") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                enabled = !guardando
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (mensaje.isNotBlank()) {
                Text(
                    text = mensaje,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            Button(
                onClick = viewModel::guardarCambios,
                modifier = Modifier.fillMaxWidth(),
                enabled = !guardando
            ) {
                if (guardando) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text("Guardar cambios")
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = onVolver,
                modifier = Modifier.fillMaxWidth(),
                enabled = !guardando
            ) {
                Text("Volver al inicio")
            }
        }

        // Diálogo flotante para seleccionar Cámara u Galería
        if (mostrarOpcionesFoto) {
            AlertDialog(
                onDismissRequest = { mostrarOpcionesFoto = false },
                title = { Text("Foto de Perfil") },
                text = { Text("¿Desde dónde deseas obtener tu foto?") },
                confirmButton = {
                    TextButton(onClick = {
                        mostrarOpcionesFoto = false
                        camaraLauncher.launch()
                    }) {
                        Text("📸 Tomar Foto")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        mostrarOpcionesFoto = false
                        galeriaLauncher.launch("image/*")
                    }) {
                        Text("🖼️ Galería")
                    }
                }
            )
        }
    }
}