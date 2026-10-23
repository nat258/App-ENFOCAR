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

@Composable
fun DocumentosScreen(
    viewModel: DocumentosViewModel,
    onVolver: () -> Unit
) {
    val documentos by viewModel.documentos.collectAsState()
    var mostrarDialogo by remember { mutableStateOf(false) }
    var tituloInput by remember { mutableStateOf("") }
    var descInput by remember { mutableStateOf("") }
    var catInput by remember { mutableStateOf("Manuales") }

    Scaffold(
        topBar = { EsfocarTopBar("Documentos") },
        floatingActionButton = {
            FloatingActionButton(onClick = { mostrarDialogo = true }) {
                Text("+")
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
                    Button(onClick = {
                        viewModel.agregarDocumentoDemo(
                            "Manual de Instrucción",
                            "Documento normativo de prueba",
                            "Manuales"
                        )
                    }) {
                        Text("Agregar Documento de Prueba")
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(documentos) { doc ->
                        DocumentoItem(
                            documento = doc,
                            onDeleteClick = { viewModel.eliminarDocumento(it) }
                        )
                    }
                }
            }
        }

        if (mostrarDialogo) {
            AlertDialog(
                onDismissRequest = { mostrarDialogo = false },
                title = { Text("Nuevo Documento") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = tituloInput,
                            onValueChange = { tituloInput = it },
                            label = { Text("Título") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = descInput,
                            onValueChange = { descInput = it },
                            label = { Text("Descripción") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = catInput,
                            onValueChange = { catInput = it },
                            label = { Text("Categoría") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        if (tituloInput.isNotBlank()) {
                            viewModel.agregarDocumentoDemo(tituloInput, descInput, catInput)
                            tituloInput = ""
                            descInput = ""
                            mostrarDialogo = false
                        }
                    }) {
                        Text("Guardar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { mostrarDialogo = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}
