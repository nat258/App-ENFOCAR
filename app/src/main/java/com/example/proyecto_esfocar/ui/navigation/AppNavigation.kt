package com.example.proyecto_esfocar.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyecto_esfocar.data.repository.UsuarioRepository
import com.example.proyecto_esfocar.model.Usuario
import com.example.proyecto_esfocar.ui.screens.BienvenidaScreen
import com.example.proyecto_esfocar.ui.screens.ConfiguracionesScreen
import com.example.proyecto_esfocar.ui.screens.DocumentosScreen
import com.example.proyecto_esfocar.ui.screens.GestionarUsuariosScreen
import com.example.proyecto_esfocar.ui.screens.HomeScreen
import com.example.proyecto_esfocar.ui.screens.LoginScreen
import com.example.proyecto_esfocar.ui.screens.PerfilScreen
import com.example.proyecto_esfocar.ui.screens.RegistroScreen
import com.example.proyecto_esfocar.viewmodel.DocumentosViewModel
import com.example.proyecto_esfocar.viewmodel.LoginViewModel
import com.example.proyecto_esfocar.viewmodel.PerfilViewModel
import com.example.proyecto_esfocar.viewmodel.RegistroViewModel

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Registro : Screen("registro")
    object Home : Screen("home")
    object Documentos : Screen("documentos")
    object Bienvenida : Screen("bienvenida")
    object Perfil : Screen("perfil")
    object GestionarUsuarios : Screen("gestionar_usuarios")
    object Configuraciones : Screen("configuraciones")
}

@Composable
fun AppNavigation(
    loginViewModel: LoginViewModel,
    registroViewModel: RegistroViewModel,
    documentosViewModel: DocumentosViewModel,
    perfilViewModel: PerfilViewModel,
    usuarioRepository: UsuarioRepository
) {
    val navController = rememberNavController()
    var usuarioActual by remember { mutableStateOf<Usuario?>(null) }

    NavHost(navController = navController, startDestination = Screen.Bienvenida.route) {
        composable(Screen.Bienvenida.route) {
            BienvenidaScreen(
                onIniciarSesion = { navController.navigate(Screen.Login.route) },
                onRegistrarse = { navController.navigate(Screen.Registro.route) }
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(
                viewModel = loginViewModel,
                onLoginExitoso = { usuario ->
                    usuarioActual = usuario
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onIrARegistro = { navController.navigate(Screen.Registro.route) }
            )
        }

        composable(Screen.Registro.route) {
            RegistroScreen(
                viewModel = registroViewModel,
                onRegistroExitoso = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Registro.route) { inclusive = true }
                    }
                },
                onVolverALogin = { navController.popBackStack() }
            )
        }

        composable(Screen.Home.route) {
            val usuario = usuarioActual
            if (usuario != null) {
                HomeScreen(
                    usuario = usuario,
                    onIrADocumentos = { navController.navigate(Screen.Documentos.route) },
                    onIrAGestionarUsuarios = { navController.navigate(Screen.GestionarUsuarios.route) },
                    onIrAPerfil = { navController.navigate(Screen.Perfil.route) },
                    onIrAConfiguraciones = { navController.navigate(Screen.Configuraciones.route) },
                    onCerrarSesion = {
                        usuarioActual = null
                        navController.navigate(Screen.Login.route) {
                            popUpTo(navController.graph.id) { inclusive = true }
                        }
                    }
                )
            } else {
                navController.navigate(Screen.Login.route) {
                    popUpTo(navController.graph.id) { inclusive = true }
                }
            }
        }

        composable(Screen.Perfil.route) {
            val usuario = usuarioActual
            if (usuario != null) {
                PerfilScreen(
                    usuario = usuario,
                    viewModel = perfilViewModel,
                    onVolver = { navController.popBackStack() },
                    onIrADocumentos = { navController.navigate(Screen.Documentos.route) },
                    onIrAConfiguraciones = { navController.navigate(Screen.Configuraciones.route) }
                )
            } else {
                navController.navigate(Screen.Login.route) {
                    popUpTo(navController.graph.id) { inclusive = true }
                }
            }
        }

        composable(Screen.Documentos.route) {
            val usuario = usuarioActual
            if (usuario != null) {
                DocumentosScreen(
                    viewModel = documentosViewModel,
                    esAdministrador = usuario.rol == "Administrador",
                    onVolver = { navController.popBackStack() },
                    onIrAPerfil = { navController.navigate(Screen.Perfil.route) },
                    onIrAConfiguraciones = { navController.navigate(Screen.Configuraciones.route) }
                )
            } else {
                navController.navigate(Screen.Login.route) {
                    popUpTo(navController.graph.id) { inclusive = true }
                }
            }
        }

        composable(Screen.GestionarUsuarios.route) {
            val usuario = usuarioActual
            if (usuario != null && usuario.rol == "Administrador") {
                GestionarUsuariosScreen(
                    usuarioRepository = usuarioRepository,
                    onVolver = { navController.popBackStack() }
                )
            } else {
                navController.navigate(Screen.Login.route) {
                    popUpTo(navController.graph.id) { inclusive = true }
                }
            }
        }

        composable(Screen.Configuraciones.route) {
            ConfiguracionesScreen(
                onIrAHome = { navController.navigate(Screen.Home.route) },
                onIrAPerfil = { navController.navigate(Screen.Perfil.route) },
                onIrADocumentos = { navController.navigate(Screen.Documentos.route) },
                onCerrarSesion = {
                    usuarioActual = null
                    navController.navigate(Screen.Login.route) {
                        popUpTo(navController.graph.id) { inclusive = true }
                    }
                }
            )
        }
    }
}