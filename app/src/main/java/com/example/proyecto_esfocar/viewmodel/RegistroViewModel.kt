package com.example.proyecto_esfocar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto_esfocar.data.repository.UsuarioRepository
import com.example.proyecto_esfocar.model.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegistroViewModel(private val repository: UsuarioRepository) : ViewModel() {

    var nombre = MutableStateFlow("")
    var email = MutableStateFlow("")
    var password = MutableStateFlow("")
    var rol = MutableStateFlow("Estudiante")

    private val _registroState = MutableStateFlow<RegistroState>(RegistroState.Idle)
    val registroState: StateFlow<RegistroState> = _registroState

    fun registrarUsuario() {
        val nomVal = nombre.value.trim()
        val emailVal = email.value.trim()
        val passVal = password.value.trim()
        val rolVal = rol.value

        if (nomVal.isEmpty() || emailVal.isEmpty() || passVal.isEmpty()) {
            _registroState.value = RegistroState.Error("Por favor complete todos los campos")
            return
        }

        viewModelScope.launch {
            _registroState.value = RegistroState.Loading
            if (repository.existeEmail(emailVal)) {
                _registroState.value = RegistroState.Error("El correo electrónico ya está registrado")
            } else {
                val nuevoUsuario = Usuario(nombre = nomVal, email = emailVal, password = passVal, rol = rolVal)
                repository.registrarUsuario(nuevoUsuario)
                _registroState.value = RegistroState.Success
            }
        }
    }

    fun resetState() {
        _registroState.value = RegistroState.Idle
    }
}

sealed class RegistroState {
    object Idle : RegistroState()
    object Loading : RegistroState()
    object Success : RegistroState()
    data class Error(val mensaje: String) : RegistroState()
}
