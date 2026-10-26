package com.example.proyecto_esfocar.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.proyecto_esfocar.ui.components.DocumentoItem
import com.example.proyecto_esfocar.ui.components.EsfocarTopBar
import com.example.proyecto_esfocar.viewmodel.DocumentosViewModel
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.ui.platform.LocalContext
import android.content.Intent
import android.widget.Toast

@Composable
fun DocumentosScreen(
    viewModel: DocumentosViewModel,
    esAdministrador: Boolean,
    onVolver: () -> Unit

) {
    val context = LocalContext.current
    fun abrirPDF(url: String) {
        if (url.isBlank()) {
            Toast.makeText(
                context,
                "Este documento no tiene un PDF adjunto",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        try {
            val uri = Uri.parse(url)

            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(uri, "application/pdf")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            context.startActivity(intent)

        } catch (e: Exception) {
            Toast.makeText(
                context,
                "No se pudo abrir el PDF. Comprueba que tienes una aplicación para leer archivos PDF.",
                Toast.LENGTH_LONG
            ).show()
        }
    }
    val documentos by viewModel.documentos.collectAsState()
    var mostrarDialogo by remember { mutableStateOf(false) }
    var tituloInput by remember { mutableStateOf("") }
    var descInput by remember { mutableStateOf("") }
    var catInput by remember { mutableStateOf("Manuales") }
    var archivoSeleccionado by remember {mutableStateOf<Uri?>(null)}
    var nombreArchivo by remember {mutableStateOf("")}
    val selectorPDF = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        if (uri != null) {
            try {
                context.contentResolver.takePersistableUriPermission(
                    uri,
                    android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
                )

                archivoSeleccionado = uri
                nombreArchivo = uri.lastPathSegment
                    ?.substringAfterLast("/")
                    ?: "PDF seleccionado"

            } catch (e: SecurityException) {
                archivoSeleccionado = uri
                nombreArchivo = uri.lastPathSegment
                    ?.substringAfterLast("/")
                    ?: "PDF seleccionado"
            }
        }

    }


    Scaffold(
        topBar = { EsfocarTopBar("Documentos") },
        floatingActionButton = {
            if (esAdministrador) {
                FloatingActionButton(
                    onClick = {
                        mostrarDialogo = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Añadir documento"
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            if (documentos.isEmpty()) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "No hay documentos disponibles",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(documentos) { doc ->
                        DocumentoItem(
                            documento = doc,
                            esAdministrador = esAdministrador,
                            onDeleteClick = { viewModel.eliminarDocumento(it) },
                            onAbrirClick = { documento ->
                                abrirPDF(documento.url)

                            }
                        )
                    }
                }
            }
        }

        if (mostrarDialogo) {
                AlertDialog(
                    onDismissRequest = {
                        mostrarDialogo = false
                    },
                    title = {
                        Text(
                            text = "Nuevo documento",
                            style = MaterialTheme.typography.headlineSmall
                        )
                    },
                    text = {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .verticalScroll(rememberScrollState()),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {

                            // Seleccionar el archivo PDF
                            OutlinedButton(
                                onClick = {
                                    selectorPDF.launch(arrayOf("application/pdf"))
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.AttachFile,
                                    contentDescription = "Adjuntar PDF"
                                )

                                Spacer(modifier = Modifier.width(8.dp))

                                Text("Seleccionar archivo PDF")
                            }

                            // Mostrar el archivo seleccionado
                            if (nombreArchivo.isNotBlank()) {
                                Text(
                                    text = "Archivo: $nombreArchivo",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }

                            // Título del documento
                            OutlinedTextField(
                                value = tituloInput,
                                onValueChange = {
                                    tituloInput = it
                                },
                                label = {
                                    Text("Título")
                                },
                                placeholder = {
                                    Text("Ej. Manual de instrucción")
                                },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth()
                            )

                            // Descripción
                            OutlinedTextField(
                                value = descInput,
                                onValueChange = {
                                    descInput = it
                                },
                                label = {
                                    Text("Descripción")
                                },
                                placeholder = {
                                    Text("Describe brevemente el documento")
                                },
                                minLines = 2,
                                maxLines = 3,
                                modifier = Modifier.fillMaxWidth()
                            )

                            // Categoría
                            OutlinedTextField(
                                value = catInput,
                                onValueChange = {
                                    catInput = it
                                },
                                label = {
                                    Text("Categoría")
                                },
                                placeholder = {
                                    Text("Ej. Manuales")
                                },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                if (tituloInput.isNotBlank()) {
                                    viewModel.agregarDocumento(
                                        titulo = tituloInput.trim(),
                                        descripcion = descInput.trim(),
                                        categoria = catInput.trim(),
                                        urlArchivo = archivoSeleccionado?.toString() ?: ""
                                    )

                                    tituloInput = ""
                                    descInput = ""
                                    catInput = "Manuales"
                                    nombreArchivo = ""
                                    archivoSeleccionado = null
                                    mostrarDialogo = false
                                }
                            },
                            enabled = tituloInput.isNotBlank() && archivoSeleccionado != null
                        ) {
                            Text("Guardar documento")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                mostrarDialogo = false
                            }
                        ) {
                            Text("Cancelar")
                        }
                    }
                )


        }
    }
}
