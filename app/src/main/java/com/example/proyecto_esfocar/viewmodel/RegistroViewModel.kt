package com.example.proyecto_esfocar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto_esfocar.data.repository.UsuarioRepository
import com.example.proyecto_esfocar.model.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegistroViewModel(private val repository: UsuarioRepository) : ViewModel() {

    // Variables ordenadas
    var rut = MutableStateFlow("")
    var nombre = MutableStateFlow("")
    var email = MutableStateFlow("")
    var telefono = MutableStateFlow("")
    var fechaNacimiento = MutableStateFlow("")
    var direccion = MutableStateFlow("")
    var password = MutableStateFlow("")
    var confirmarPassword = MutableStateFlow("")

    private val _registroState = MutableStateFlow<RegistroState>(RegistroState.Idle)
    val registroState: StateFlow<RegistroState> = _registroState

    fun registrarUsuario() {
        val rutVal = rut.value.trim()
        val nomVal = capitalizarNombre(nombre.value)
        val emailVal = email.value.trim().lowercase()
        val telVal = telefono.value.trim()
        val fechaVal = fechaNacimiento.value.trim()
        val dirVal = direccion.value.trim()
        val passVal = password.value.trim()
        val confPassVal = confirmarPassword.value.trim()


        // Validar que no haya campos vacíos
        if (rutVal.isEmpty() || nomVal.isEmpty() || emailVal.isEmpty() ||
            telVal.isEmpty() || fechaVal.isEmpty() || dirVal.isEmpty() ||
            passVal.isEmpty() || confPassVal.isEmpty()) {
            _registroState.value = RegistroState.Error("Por favor complete todos los campos")
            return
        }

        // Validar formato de correo
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailVal).matches()) {
            _registroState.value = RegistroState.Error("Ingrese un correo electrónico válido")
            return
        }

        // Validar que las contraseñas coincidan
        if (passVal != confPassVal) {
            _registroState.value = RegistroState.Error("Las contraseñas no coinciden")
            return
        }

        // Validar longitud mínima de contraseña
        if (passVal.length < 6) {
            _registroState.value = RegistroState.Error("La contraseña debe tener al menos 6 caracteres")
            return
        }

        viewModelScope.launch {
            _registroState.value = RegistroState.Loading
            if (repository.existeEmail(emailVal)) {
                _registroState.value = RegistroState.Error("El correo electrónico ya está registrado")
            } else {
                val nuevoUsuario = Usuario(
                    nombre = nomVal,
                    email = emailVal,
                    password = passVal,
                    rol = "Estudiante",
                    rut = rutVal,
                    telefono = telVal,
                    fechaNacimiento = fechaVal,
                    direccion = dirVal
                )
                // Guarda en la base de datos y obtiene el id asignado
                val idGenerado = repository.registrarUsuario(nuevoUsuario)
                val usuarioCreado = nuevoUsuario.copy(id = idGenerado.toInt())

                _registroState.value = RegistroState.Success(usuarioCreado)
            }
        }
    }

    fun resetState() {
        rut.value = ""
        nombre.value = ""
        email.value = ""
        telefono.value = ""
        fechaNacimiento.value = ""
        direccion.value = ""
        password.value = ""
        confirmarPassword.value = ""
        _registroState.value = RegistroState.Idle
    }
}

sealed class RegistroState {
    object Idle : RegistroState()
    object Loading : RegistroState()
    data class Success(val usuario: Usuario) : RegistroState() // <-- Pasa el usuario creado
    data class Error(val mensaje: String) : RegistroState()
}


fun capitalizarNombre(nombre: String): String {
    if (nombre.isBlank()) return ""
    return nombre.trim().split(" ")
        .filter { it.isNotBlank() }
        .joinToString(" ") { palabra ->
            palabra.lowercase(java.util.Locale.getDefault()).replaceFirstChar { char ->
                if (char.isLowerCase()) char.titlecase(java.util.Locale.getDefault()) else char.toString()
            }
        }
}