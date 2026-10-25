package com.example.proyecto_esfocar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto_esfocar.data.repository.UsuarioRepository
import com.example.proyecto_esfocar.model.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PerfilViewModel(
    private val repository: UsuarioRepository
) : ViewModel() {

    private val _nombre = MutableStateFlow("")
    val nombre: StateFlow<String> = _nombre.asStateFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _telefono = MutableStateFlow("")
    val telefono: StateFlow<String> = _telefono.asStateFlow()

    private val _rut = MutableStateFlow("")
    val rut: StateFlow<String> = _rut.asStateFlow()

    private val _fechaNacimiento = MutableStateFlow("")
    val fechaNacimiento: StateFlow<String> = _fechaNacimiento.asStateFlow()

    private val _direccion = MutableStateFlow("")
    val direccion: StateFlow<String> = _direccion.asStateFlow()

    private val _fotoPerfil = MutableStateFlow("")
    val fotoPerfil: StateFlow<String> = _fotoPerfil.asStateFlow()

    private val _mensaje = MutableStateFlow("")
    val mensaje: StateFlow<String> = _mensaje.asStateFlow()

    private val _modoEdicion = MutableStateFlow(false)
    val modoEdicion: StateFlow<Boolean> = _modoEdicion.asStateFlow()

    private val _guardando = MutableStateFlow(false)
    val guardando: StateFlow<Boolean> = _guardando.asStateFlow()

    private var usuarioActual: Usuario? = null

    // Carga los datos del usuario que inició sesión
    fun cargarUsuario(usuario: Usuario) {
        usuarioActual = usuario
        _nombre.value = usuario.nombre
        _email.value = usuario.email
        _telefono.value = usuario.telefono
        _rut.value = usuario.rut
        _fechaNacimiento.value = usuario.fechaNacimiento
        _direccion.value = usuario.direccion
        _fotoPerfil.value = usuario.fotoPerfil
        _mensaje.value = ""
    }

    // Métodos para cambiar los campos
    fun cambiarNombre(nuevoNombre: String) { _nombre.value = nuevoNombre }
    fun cambiarEmail(nuevoEmail: String) { _email.value = nuevoEmail }
    fun cambiarTelefono(nuevoTelefono: String) { _telefono.value = nuevoTelefono }
    fun cambiarRut(nuevoRut: String) { _rut.value = nuevoRut }
    fun cambiarFechaNacimiento(nuevaFecha: String) { _fechaNacimiento.value = nuevaFecha }
    fun cambiarDireccion(nuevaDireccion: String) { _direccion.value = nuevaDireccion }
    fun cambiarFotoPerfil(nuevaUri: String) { _fotoPerfil.value = nuevaUri }

    fun cambiarMensaje(nuevoMensaje: String) {
        _mensaje.value = nuevoMensaje
    }

    fun alternarModoEdicion() {
        if (_modoEdicion.value) {
            usuarioActual?.let { cargarUsuario(it) }
        }
        _modoEdicion.value = !_modoEdicion.value
        _mensaje.value = ""
    }

    // Guarda los cambios en la base de datos de Room
    fun guardarCambios() {
        val usuario = usuarioActual
        val nombreNuevo = _nombre.value.trim()
        val emailNuevo = _email.value.trim().lowercase()
        val telefonoNuevo = _telefono.value.trim()
        val rutNuevo = _rut.value.trim()
        val fechaNacimientoNueva = _fechaNacimiento.value.trim()
        val direccionNueva = _direccion.value.trim()
        val fotoNueva = _fotoPerfil.value.trim()

        if (usuario == null) {
            _mensaje.value = "No se ha cargado ningún usuario."
            return
        }

        if (nombreNuevo.isBlank() || emailNuevo.isBlank()) {
            _mensaje.value = "Nombre y correo son obligatorios."
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailNuevo).matches()) {
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
                    email = emailNuevo,
                    telefono = telefonoNuevo,
                    rut = rutNuevo,
                    fechaNacimiento = fechaNacimientoNueva,
                    direccion = direccionNueva,
                    fotoPerfil = fotoNueva
                )

                val filasActualizadas = repository.actualizarUsuario(usuarioActualizado)

                if (filasActualizadas > 0) {
                    usuarioActual = usuarioActualizado
                    _nombre.value = usuarioActualizado.nombre
                    _email.value = usuarioActualizado.email
                    _telefono.value = usuarioActualizado.telefono
                    _rut.value = usuarioActualizado.rut
                    _fechaNacimiento.value = usuarioActualizado.fechaNacimiento
                    _direccion.value = usuarioActualizado.direccion
                    _fotoPerfil.value = usuarioActualizado.fotoPerfil

                    _mensaje.value = "Datos actualizados correctamente."
                    _modoEdicion.value = false
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