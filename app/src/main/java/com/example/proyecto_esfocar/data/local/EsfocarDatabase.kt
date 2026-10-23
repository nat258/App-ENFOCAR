package com.example.proyecto_esfocar.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.proyecto_esfocar.model.Documento
import com.example.proyecto_esfocar.model.Usuario

@Database(entities = [Usuario::class, Documento::class], version = 1, exportSchema = false)
abstract class EsfocarDatabase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao
    abstract fun documentoDao(): DocumentoDao

    companion object {
        @Volatile
        private var INSTANCE: EsfocarDatabase? = null

        fun getDatabase(context: Context): EsfocarDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EsfocarDatabase::class.java,
                    "esfocar_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
