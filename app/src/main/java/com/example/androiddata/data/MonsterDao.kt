package com.example.androiddata.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MonsterDao {
    @Insert
    fun insertAll(monsters: List<Monster>)

    @Query("SELECT * From monsters")
    suspend fun getAll(): List<Monster>

    @Insert
    suspend fun insert(monster: Monster)

    @Query("Delete from monsters")
    suspend fun deleteAll()

}