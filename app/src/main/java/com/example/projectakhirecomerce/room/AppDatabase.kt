package com.example.projectakhirecomerce.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.projectakhirecomerce.model.CartEntity
import com.example.projectakhirecomerce.model.UserEntity

@Database(entities = [UserEntity::class, CartEntity::class], version = 3) //ganti versi tiap ada perubahan di database
@TypeConverters(AppConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun cartDao(): CartDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, "app_database"
                    )
                        .fallbackToDestructiveMigration() //ini ditambahkan jika memiliki leih dari 1 entitiy
                        .build()
                }
            }
            return INSTANCE as AppDatabase
        }
    }
}
