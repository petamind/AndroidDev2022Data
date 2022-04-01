package com.example.androiddata.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Monster::class], version = 1)
abstract class MonsterDB: RoomDatabase() {
    abstract fun monsterDao(): MonsterDao

    companion object {
        @Volatile
        private var INSTANCE: MonsterDB? = null

        fun getDatabase(context: Context): MonsterDB {
            if (INSTANCE == null) {
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        MonsterDB::class.java,
                        "monsters.db"
                    ).build()
                }
            }
            return  INSTANCE!!
        }
    }
}