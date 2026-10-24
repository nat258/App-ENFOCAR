package com.example.proyecto_esfocar.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyecto_esfocar.ui.screens.DocumentosScreen
import com.example.proyecto_esfocar.ui.screens.HomeScreen
import com.example.proyecto_esfocar.ui.screens.LoginScreen
import com.example.proyecto_esfocar.ui.screens.RegistroScreen
import com.example.proyecto_esfocar.viewmodel.DocumentosViewModel
import com.example.proyecto_esfocar.viewmodel.LoginViewModel
import com.example.proyecto_esfocar.viewmodel.RegistroViewModel
import com.example.proyecto_esfocar.ui.screens.BienvenidaScreen

sealed class Screen(val route: String) {
    object Login : Screen("Bienvenida")
    object Registro : Screen("registro")
    object Home : Screen("home")
    object Documentos : Screen("documentos")
    object Bienvenida : Screen("bienvenida")
}

@Composable
fun AppNavigation(
    loginViewModel: LoginViewModel,
    registroViewModel: RegistroViewModel,
    documentosViewModel: DocumentosViewModel

) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Bienvenida.route) {
        composable(Screen.Bienvenida.route) {
            BienvenidaScreen(
                onIniciarSesion = {
                    navController.navigate(Screen.Login.route)
                },
                onRegistrarse = {
                    navController.navigate(Screen.Registro.route)
                }
            )
        }
        composable(Screen.Login.route) {
            LoginScreen(
                viewModel = loginViewModel,
                onLoginExitoso = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onIrARegistro = {
                    navController.navigate(Screen.Registro.route)
                }
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
                onVolverALogin = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                onIrADocumentos = {
                    navController.navigate(Screen.Documentos.route)
                },
                onCerrarSesion = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Documentos.route) {
            DocumentosScreen(
                viewModel = documentosViewModel,
                onVolver = {
                    navController.popBackStack()
                }
            )
        }
    }
}
