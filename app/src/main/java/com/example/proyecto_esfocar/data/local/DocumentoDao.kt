package com.example.proyecto_esfocar.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.proyecto_esfocar.model.Documento
import kotlinx.coroutines.flow.Flow

@Dao
interface DocumentoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarDocumento(documento: Documento): Long

    @Query("SELECT * FROM documentos ORDER BY id DESC")
    fun obtenerTodosLosDocumentos(): Flow<List<Documento>>

    @Query("SELECT * FROM documentos WHERE categoria = :categoria ORDER BY id DESC")
    fun obtenerDocumentosPorCategoria(categoria: String): Flow<List<Documento>>

    @Delete
    suspend fun eliminarDocumento(documento: Documento)
}
