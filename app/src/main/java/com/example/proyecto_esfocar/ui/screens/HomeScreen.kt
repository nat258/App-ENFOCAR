package com.example.proyecto_esfocar.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.proyecto_esfocar.model.Usuario
import com.example.proyecto_esfocar.ui.components.EsfocarBottomBar
import com.example.proyecto_esfocar.ui.components.EsfocarTopBar

@Composable
fun HomeScreen(
    usuario: Usuario,
    onIrADocumentos: () -> Unit,
    onIrAGestionarUsuarios: () -> Unit,
    onIrAPerfil: () -> Unit,
    onIrAConfiguraciones: () -> Unit,
    onCerrarSesion: () -> Unit
) {
    Scaffold(
        topBar = { EsfocarTopBar("ESFOCAR - Inicio") },
        bottomBar = {
            EsfocarBottomBar(
                rutaActual = "home",
                onIrAHome = { },
                onIrAPerfil = onIrAPerfil,
                onIrADocumentos = onIrADocumentos,
                onIrAConfiguraciones = onIrAConfiguraciones
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
                text = "¡Hola  ${usuario.nombre}!",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Plataforma de apoyo formativo y consulta de documentos.",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            Button(
                onClick = onIrADocumentos,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Ver Documentos y Recursos")
            }

            if (usuario.rol == "Administrador") {
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedButton(
                    onClick = onIrAGestionarUsuarios,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text("Gestionar usuarios")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = onIrAPerfil,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Mi perfil")
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = onCerrarSesion,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Cerrar Sesión")
            }
        }
    }
}