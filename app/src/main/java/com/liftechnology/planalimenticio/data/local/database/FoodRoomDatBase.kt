package com.liftechnology.planalimenticio.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.liftechnology.planalimenticio.data.local.dao.FoodDao
import com.liftechnology.planalimenticio.data.local.entity.FoodEntity

@Database(entities = [FoodEntity::class], version = 2, exportSchema = false)
abstract class FoodRoomDatBase : RoomDatabase() {

    abstract  fun FoodDao() : FoodDao

    companion object{
        @Volatile
        private var INSTANCE : FoodRoomDatBase? = null
        fun getDataBase(context: Context): FoodRoomDatBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FoodRoomDatBase::class.java,
                    "food_database"
                )
                .fallbackToDestructiveMigration() // En desarrollo: recrea la BD si hay cambios de esquema
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}