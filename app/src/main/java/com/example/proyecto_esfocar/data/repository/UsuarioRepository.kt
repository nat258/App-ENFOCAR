package com.example.proyecto_esfocar.data.repository

import com.example.proyecto_esfocar.data.local.UsuarioDao
import com.example.proyecto_esfocar.model.Usuario

class UsuarioRepository(private val usuarioDao: UsuarioDao) {

    suspend fun registrarUsuario(usuario: Usuario): Long {
        return usuarioDao.insertarUsuario(usuario)
    }

    suspend fun login(email: String, password: String): Usuario? {
        return usuarioDao.login(email, password)
    }

    suspend fun existeEmail(email: String): Boolean {
        return usuarioDao.obtenerUsuarioPorEmail(email) != null
    }

    suspend fun actualizarUsuario(usuario: Usuario): Int {
        return usuarioDao.actualizarUsuario(usuario)
    }

    suspend fun eliminarUsuario(usuario: Usuario): Int {
        // No permite eliminar cuentas de administrador.
        if (usuario.rol == "Administrador") {
            return 0
        }

        return usuarioDao.eliminarUsuario(usuario)
    }

    suspend fun crearAdministradorDemo() {
        val email = "admin@esfocar.cl"

        // Comprobar si el administrador ya existe
        if (usuarioDao.obtenerUsuarioPorEmail(email) == null) {

            // Crear la cuenta inicial del administrador
            val administrador = Usuario(
                nombre = "Administrador",
                email = email,
                password = "Admin123",
                rol = "Administrador"
            )

            usuarioDao.insertarUsuario(administrador)
        }
    }
    suspend fun obtenerTodosLosUsuarios(): List<Usuario> {
        return usuarioDao.obtenerTodosLosUsuarios()
    }
}
