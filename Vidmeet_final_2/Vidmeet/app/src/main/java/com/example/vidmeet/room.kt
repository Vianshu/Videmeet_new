package com.example.vidmeet

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase


@Entity
data class room(
    @PrimaryKey (autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "room") val r: String?,
    @ColumnInfo(name = "time") val t: String?,
    @ColumnInfo(name = "date") val d: String?,
    @ColumnInfo(name = "Location") val l: String?
)
@Dao
interface roomDao {
    @Query("SELECT * FROM room")
    fun getAll(): List<room>

    @Insert
    fun insertAll(vararg r: room)

    @Query("DELETE FROM room")
    fun clear()
}

@Database(entities = [room::class], version = 5)
abstract class roomdb : RoomDatabase() {
    abstract fun roomDao(): roomDao
}