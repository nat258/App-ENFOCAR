package com.example.proyecto_esfocar.data.repository

import com.example.proyecto_esfocar.data.local.DocumentoDao
import com.example.proyecto_esfocar.model.Documento
import kotlinx.coroutines.flow.Flow

class DocumentoRepository(private val documentoDao: DocumentoDao) {

    fun obtenerTodosLosDocumentos(): Flow<List<Documento>> {
        return documentoDao.obtenerTodosLosDocumentos()
    }

    fun obtenerDocumentosPorCategoria(categoria: String): Flow<List<Documento>> {
        return documentoDao.obtenerDocumentosPorCategoria(categoria)
    }

    suspend fun agregarDocumento(documento: Documento): Long {
        return documentoDao.insertarDocumento(documento)
    }

    suspend fun eliminarDocumento(documento: Documento) {
        documentoDao.eliminarDocumento(documento)
    }
}
