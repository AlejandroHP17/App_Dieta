package com.liftechnology.planalimenticio.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.liftechnology.planalimenticio.data.local.dao.FoodDao
import com.liftechnology.planalimenticio.data.local.entity.FoodEntity

@Database(entities = [FoodEntity::class], version = 1, exportSchema = false)
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
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}