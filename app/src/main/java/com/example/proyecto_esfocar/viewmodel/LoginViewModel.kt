package com.example.proyecto_esfocar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto_esfocar.data.repository.UsuarioRepository
import com.example.proyecto_esfocar.model.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UsuarioRepository) : ViewModel() {

    var email = MutableStateFlow("")
    var password = MutableStateFlow("")

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    fun realizarLogin() {
        val emailVal = email.value.trim()
        val passVal = password.value.trim()

        if (emailVal.isEmpty() || passVal.isEmpty()) {
            _loginState.value = LoginState.Error("Por favor complete todos los campos")
            return
        }

        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            val usuario = repository.login(emailVal, passVal)
            if (usuario != null) {
                _loginState.value = LoginState.Success(usuario)
            } else {
                _loginState.value = LoginState.Error("Credenciales incorrectas o usuario no registrado")
            }
        }
    }

    fun resetState() {
        _loginState.value = LoginState.Idle
    }
}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val usuario: Usuario) : LoginState()
    data class Error(val mensaje: String) : LoginState()
}
