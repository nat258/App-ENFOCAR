package com.example.proyecto_esfocar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.proyecto_esfocar.data.local.EsfocarDatabase
import com.example.proyecto_esfocar.data.repository.DocumentoRepository
import com.example.proyecto_esfocar.data.repository.UsuarioRepository
import com.example.proyecto_esfocar.ui.navigation.AppNavigation
import com.example.proyecto_esfocar.ui.theme.Proyecto_EsfocarTheme
import com.example.proyecto_esfocar.viewmodel.DocumentosViewModel
import com.example.proyecto_esfocar.viewmodel.LoginViewModel
import com.example.proyecto_esfocar.viewmodel.RegistroViewModel
import com.example.proyecto_esfocar.viewmodel.PerfilViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = EsfocarDatabase.getDatabase(this)
        val usuarioRepository = UsuarioRepository(database.usuarioDao())
        val documentoRepository = DocumentoRepository(database.documentoDao())

        val loginViewModel = LoginViewModel(usuarioRepository)
        val registroViewModel = RegistroViewModel(usuarioRepository)
        val documentosViewModel = DocumentosViewModel(documentoRepository)
        val perfilViewModel = PerfilViewModel(usuarioRepository)

        setContent {
            Proyecto_EsfocarTheme {
                AppNavigation(
                    loginViewModel = loginViewModel,
                    registroViewModel = registroViewModel,
                    documentosViewModel = documentosViewModel,
                    perfilViewModel = perfilViewModel
                )
            }
        }
    }
}
