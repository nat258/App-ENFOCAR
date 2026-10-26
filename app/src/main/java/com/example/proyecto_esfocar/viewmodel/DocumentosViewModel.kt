package com.example.proyecto_esfocar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto_esfocar.data.repository.DocumentoRepository
import com.example.proyecto_esfocar.model.Documento
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DocumentosViewModel(private val repository: DocumentoRepository) : ViewModel() {

    val documentos: StateFlow<List<Documento>> = repository.obtenerTodosLosDocumentos()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun agregarDocumento(
        titulo: String,
        descripcion: String,
        categoria: String,
        urlArchivo: String
    ) {
        viewModelScope.launch {
            val nuevoDoc = Documento(
                titulo = titulo,
                descripcion = descripcion,
                categoria = categoria,
                fechaSubida = java.time.LocalDate.now().toString(),
                url = urlArchivo
            )

            repository.agregarDocumento(nuevoDoc)
        }
    }

    fun eliminarDocumento(documento: Documento) {
        viewModelScope.launch {
            repository.eliminarDocumento(documento)
        }
    }
}
