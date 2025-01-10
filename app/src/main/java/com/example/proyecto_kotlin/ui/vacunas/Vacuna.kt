package com.example.proyecto_kotlin.ui.vacunas

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import android.content.Context
import androidx.room.Room
import androidx.lifecycle.LiveData
import androidx.room.*

@Entity(tableName = "vacunas")
data class Vacuna(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val fechaAplicacion: String,
    val proximaDosis: String? = null,
    var mascotaId: Int
)


@Dao
interface VacunaDao {
    @Query("SELECT * FROM vacunas")
    fun getAll(): LiveData<List<Vacuna>>

    @Query("SELECT * FROM vacunas WHERE id = :id")
    fun getById(id: Int): LiveData<Vacuna?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vacuna: Vacuna)

    @Update
    suspend fun update(vacuna: Vacuna)

    @Delete
    suspend fun delete(vacuna: Vacuna)
}

@Database(entities = [Vacuna::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun vacunaDao(): VacunaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "vacunas_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}