package com.example.proyecto_esfocar.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto_esfocar.data.repository.UsuarioRepository
import com.example.proyecto_esfocar.model.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PerfilViewModel(
    private val repository: UsuarioRepository
) : ViewModel() {

    private val _nombre = MutableStateFlow("")
    val nombre: StateFlow<String> = _nombre

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _mensaje = MutableStateFlow("")
    val mensaje: StateFlow<String> = _mensaje

    private val _guardando = MutableStateFlow(false)
    val guardando: StateFlow<Boolean> = _guardando

    private var usuarioActual: Usuario? = null

    // Carga los datos del usuario que inició sesión.
    fun cargarUsuario(usuario: Usuario) {
        usuarioActual = usuario
        _nombre.value = usuario.nombre
        _email.value = usuario.email
        _mensaje.value = ""
    }

    // Actualiza el nombre mientras se escribe.
    fun cambiarNombre(nuevoNombre: String) {
        _nombre.value = nuevoNombre
    }

    // Actualiza el correo mientras se escribe.
    fun cambiarEmail(nuevoEmail: String) {
        _email.value = nuevoEmail
    }

    // Guarda los cambios en la base de datos.
    fun guardarCambios() {
        val usuario = usuarioActual
        val nombreNuevo = _nombre.value.trim()
        val emailNuevo = _email.value.trim().lowercase()

        if (usuario == null) {
            _mensaje.value = "No se ha cargado ningún usuario."
            return
        }

        if (nombreNuevo.isBlank() || emailNuevo.isBlank()) {
            _mensaje.value = "Completa todos los campos."
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS
                .matcher(emailNuevo)
                .matches()
        ) {
            _mensaje.value = "Introduce un correo electrónico válido."
            return
        }

        viewModelScope.launch {
            _guardando.value = true

            try {
                val emailExiste = repository.existeEmail(emailNuevo)

                if (emailNuevo != usuario.email.lowercase() && emailExiste) {
                    _mensaje.value = "Ese correo ya está registrado."
                    return@launch
                }

                val usuarioActualizado = usuario.copy(
                    nombre = nombreNuevo,
                    email = emailNuevo
                )

                val filasActualizadas =
                    repository.actualizarUsuario(usuarioActualizado)

                if (filasActualizadas > 0) {
                    usuarioActual = usuarioActualizado
                    _nombre.value = usuarioActualizado.nombre
                    _email.value = usuarioActualizado.email
                    _mensaje.value = "Datos actualizados correctamente."
                } else {
                    _mensaje.value = "No se pudieron guardar los cambios."
                }
            } catch (e: Exception) {
                _mensaje.value = "Ocurrió un error al actualizar los datos."
            } finally {
                _guardando.value = false
            }
        }
    }
}