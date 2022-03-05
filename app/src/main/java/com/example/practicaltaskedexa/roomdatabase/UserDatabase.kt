package com.example.practicaltaskedexa.roomdatabase

import android.content.Context
import androidx.room.*
import com.example.practicaltaskedexa.roomdatabase.daos.EmployeeDao
import com.example.practicaltaskedexa.roomdatabase.daos.UserDao
import com.example.practicaltaskedexa.roomdatabase.entities.EmployeeEntity
import com.example.practicaltaskedexa.roomdatabase.entities.UserEntity

@Database(
    entities = arrayOf(UserEntity::class, EmployeeEntity::class),
    version = 1,
    exportSchema = false
)
public abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun employeeDao(): EmployeeDao

    companion object {

        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabaseClient(context: Context): UserDatabase {

            if (INSTANCE != null) return INSTANCE!!

            synchronized(this) {

                INSTANCE = Room
                    .databaseBuilder(context, UserDatabase::class.java, "user_database")
                    .fallbackToDestructiveMigration()
                    .build()

                return INSTANCE!!

            }
        }

    }
}