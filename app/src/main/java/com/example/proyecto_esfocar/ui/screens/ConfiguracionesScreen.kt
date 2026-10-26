package com.example.proyecto_esfocar.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.proyecto_esfocar.ui.components.EsfocarBottomBar
import com.example.proyecto_esfocar.ui.components.EsfocarTopBar

@Composable
fun ConfiguracionesScreen(
    onIrAHome: () -> Unit,
    onIrAPerfil: () -> Unit,
    onIrADocumentos: () -> Unit,
    onCerrarSesion: () -> Unit
) {
    Scaffold(
        topBar = { EsfocarTopBar("Configuraciones") },
        bottomBar = {
            EsfocarBottomBar(
                rutaActual = "configuraciones",
                onIrAHome = onIrAHome,
                onIrAPerfil = onIrAPerfil,
                onIrADocumentos = onIrADocumentos,
                onIrAConfiguraciones = { }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Ajustes de la Aplicación",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onCerrarSesion,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Cerrar Sesión")
            }
        }
    }
}