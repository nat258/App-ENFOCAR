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
}
