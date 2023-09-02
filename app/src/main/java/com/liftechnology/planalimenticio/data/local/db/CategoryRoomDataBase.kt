package com.liftechnology.planalimenticio.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.liftechnology.planalimenticio.data.local.dao.CategoryDao
import com.liftechnology.planalimenticio.data.local.entity.CategoryEntity

@Database(entities = [CategoryEntity::class], version = 1, exportSchema = false)
abstract class CategoryRoomDataBase : RoomDatabase() {

    abstract fun CategoryDao(): CategoryDao
    companion object{
        @Volatile
        private var INSTANCE : CategoryRoomDataBase? = null
        fun getDataBase(context: Context): CategoryRoomDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CategoryRoomDataBase::class.java,
                    "category_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}