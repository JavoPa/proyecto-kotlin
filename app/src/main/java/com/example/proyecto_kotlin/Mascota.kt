package com.example.proyecto_kotlin

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Locale
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.TypeConverter
import java.util.Date
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


@Entity(tableName = "mascotas")
@TypeConverters(Converters::class)
data class Mascota(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val especie: String,
    val raza: String,
    var peso: Double?,
    val alergias: MutableList<String> = mutableListOf(),
    val antecedentes: MutableList<String> = mutableListOf(),
    val fechaNacimiento: String,
    val fotoUrl: String?
) {
    val edad: Int?
        get() = calcularEdad(fechaNacimiento)

    private fun calcularEdad(fechaNacimiento: String): Int? {
        val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val fechaNac = formato.parse(fechaNacimiento)
        val fechaActual = Calendar.getInstance().time

        if (fechaNac != null) {
            val edad = Calendar.getInstance().apply { time = fechaNac }
            val edadActual = Calendar.getInstance()

            var edadCalculada = edadActual.get(Calendar.YEAR) - edad.get(Calendar.YEAR)

            if (edadActual.get(Calendar.MONTH) < edad.get(Calendar.MONTH) ||
                (edadActual.get(Calendar.MONTH) == edad.get(Calendar.MONTH) && edadActual.get(
                    Calendar.DAY_OF_MONTH
                ) < edad.get(Calendar.DAY_OF_MONTH))
            ) {
                edadCalculada--
            }

            return edadCalculada
        }
        return null
    }
}

@Dao
interface MascotaDao {
    @Query("SELECT * FROM mascotas")
    fun getAll(): LiveData<List<Mascota>>

    @Query("SELECT * FROM mascotas WHERE id = :id")
    fun getById(id: Int): LiveData<Mascota?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(mascota: Mascota)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(mascotas: List<Mascota>)

    @Update
    suspend fun update(mascota: Mascota)

    @Delete
    suspend fun delete(mascota: Mascota)
}

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromStringList(value: String?): MutableList<String>? {
        val listType = object : TypeToken<MutableList<String>>() {}.type
        return value?.let { Gson().fromJson(it, listType) }
    }

    @TypeConverter
    fun stringListToString(list: MutableList<String>?): String? {
        return Gson().toJson(list)
    }
}