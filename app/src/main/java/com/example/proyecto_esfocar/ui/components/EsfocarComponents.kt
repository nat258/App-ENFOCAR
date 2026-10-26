package com.example.proyecto_esfocar.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.proyecto_esfocar.model.Documento
import com.example.proyecto_esfocar.ui.theme.AmarilloInstitucional
import androidx.compose.ui.graphics.Color
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EsfocarTopBar(
    titulo: String,
    onBackClick: (() -> Unit)? = null
) {
    TopAppBar(
        title = {
            Text(
                text = titulo,
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = {
            if (onBackClick != null) {
                IconButton(onClick = onBackClick) {
                    Text(
                        text = "←",
                        style = MaterialTheme.typography.titleLarge,
                        color = AmarilloInstitucional
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = AmarilloInstitucional,
            navigationIconContentColor = AmarilloInstitucional
        )
    )
}

@Composable
fun DocumentoItem(
    documento: Documento,
    esAdministrador: Boolean,
    onDeleteClick: (Documento) -> Unit,
    onAbrirClick: (Documento) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        onClick = { onAbrirClick(documento) }
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "📄",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = documento.titulo,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = documento.descripcion,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(4.dp))

                SuggestionChip(
                    onClick = { onAbrirClick(documento) },
                    label = { Text(documento.categoria) }
                )
            }

            if (esAdministrador) {
                TextButton(
                    onClick = { onDeleteClick(documento) }
                ) {
                    Text(
                        text = "Eliminar",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Composable
fun EsfocarBottomBar(
    rutaActual: String,
    onIrAHome: () -> Unit,
    onIrAPerfil: () -> Unit,
    onIrADocumentos: () -> Unit,
    onIrAConfiguraciones: () -> Unit
) {
    val itemColors = NavigationBarItemDefaults.colors(
        selectedIconColor = AmarilloInstitucional,
        selectedTextColor = AmarilloInstitucional,
        unselectedIconColor = Color.White.copy(alpha = 0.7f),
        unselectedTextColor = Color.White.copy(alpha = 0.7f),
        indicatorColor = MaterialTheme.colorScheme.primaryContainer
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = AmarilloInstitucional
    ) {
        // 1. Inicio
        NavigationBarItem(
            selected = rutaActual == "home",
            onClick = onIrAHome,
            icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Inicio") },
            label = { Text("Inicio") },
            colors = itemColors
        )

        // 2. Perfil / Cuenta
        NavigationBarItem(
            selected = rutaActual == "perfil",
            onClick = onIrAPerfil,
            icon = { Icon(imageVector = Icons.Default.Person, contentDescription = "Perfil") },
            label = { Text("Perfil") },
            colors = itemColors
        )

        // 3. Material de Estudio
        NavigationBarItem(
            selected = rutaActual == "documentos",
            onClick = onIrADocumentos,
            icon = { Icon(imageVector = Icons.Default.Description, contentDescription = "Estudio") },
            label = { Text("Estudio") },
            colors = itemColors
        )

        // 4. Configuraciones
        NavigationBarItem(
            selected = rutaActual == "configuraciones",
            onClick = onIrAConfiguraciones,
            icon = { Icon(imageVector = Icons.Default.Settings, contentDescription = "Ajustes") },
            label = { Text("Ajustes") },
            colors = itemColors
        )
    }
}