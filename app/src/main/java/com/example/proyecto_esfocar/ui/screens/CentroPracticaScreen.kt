package com.example.proyecto_esfocar.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.proyecto_esfocar.ui.components.EsfocarBottomBar
import com.example.proyecto_esfocar.ui.components.EsfocarTopBar
import com.example.proyecto_esfocar.ui.theme.AmarilloInstitucional

@Composable
fun CentroPracticaScreen(
    onIrALecciones: () -> Unit,
    onIrAHome: () -> Unit,
    onIrAPerfil: () -> Unit,
    onIrAConfiguraciones: () -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        topBar = { EsfocarTopBar("CENTRO DE PRÁCTICA") },
        bottomBar = {
            EsfocarBottomBar(
                rutaActual = "documentos",
                onIrAHome = onIrAHome,
                onIrAPerfil = onIrAPerfil,
                onIrADocumentos = { },
                onIrAConfiguraciones = onIrAConfiguraciones
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            // Cuadrícula 2x2 para las 4 tarjetas
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                // 1. Trivia Diaria 💡 (Ampolleta)
                item {
                    OpcionPracticaCard(
                        titulo = "TRIVIA\nDIARIA",
                        icono = Icons.Default.Lightbulb,
                        onClick = {
                            Toast.makeText(context, "Módulo de Trivia Diaria", Toast.LENGTH_SHORT).show()
                        }
                    )
                }

                // 2. Pruebas Simuladas 📝 (Examen con Lápiz)
                item {
                    OpcionPracticaCard(
                        titulo = "PRUEBAS\nSIMULADAS",
                        icono = Icons.Default.Quiz,
                        onClick = {
                            Toast.makeText(context, "Módulo de Pruebas Simuladas", Toast.LENGTH_SHORT).show()
                        }
                    )
                }

                // 3. Historial de Resultados 📈 (Gráfico)
                item {
                    OpcionPracticaCard(
                        titulo = "HISTORIAL DE\nRESULTADOS",
                        icono = Icons.Default.TrendingUp,
                        onClick = {
                            Toast.makeText(context, "Historial de Resultados", Toast.LENGTH_SHORT).show()
                        }
                    )
                }

                // 4. Lecciones Recomendadas 📖 (Libro Abierto -> Abre la lista de PDFs)
                item {
                    OpcionPracticaCard(
                        titulo = "LECCIONES\nRECOMENDADAS",
                        icono = Icons.Default.MenuBook,
                        onClick = onIrALecciones
                    )
                }
            }
        }
    }
}

// Componente de Tarjeta Verde con Íconos y Texto en Amarillo
@Composable
fun OpcionPracticaCard(
    titulo: String,
    icono: ImageVector,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary // Fondo Verde Institucional
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icono,
                contentDescription = titulo,
                tint = AmarilloInstitucional, // Ícono en Amarillo idéntico a la foto
                modifier = Modifier.size(52.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = titulo,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = AmarilloInstitucional, // Texto en Amarillo en negrita
                textAlign = TextAlign.Center
            )
        }
    }
}

