package com.tessmerandre.advancedtrackerapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tessmerandre.advancedtrackerapp.data.location.LocationDao
import com.tessmerandre.advancedtrackerapp.data.location.LocationEntity
import com.tessmerandre.advancedtrackerapp.data.user.UserDao
import com.tessmerandre.advancedtrackerapp.data.user.UserEntity

@Database(entities = [UserEntity::class, LocationEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun locationDao(): LocationDao

    companion object {

        private const val databaseName = "advancedtrackerapp-db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = buildDatabase(context)
                }
            }
            return INSTANCE!!
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                databaseName
            ).build()
        }
    }
}