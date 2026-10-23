package com.example.proyecto_esfocar.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "documentos")
data class Documento(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val titulo: String,
    val descripcion: String,
    val categoria: String,
    val fechaSubida: String,
    val url: String = ""
)
