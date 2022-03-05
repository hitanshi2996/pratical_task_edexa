package com.example.practicaltaskedexa.roomdatabase.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.practicaltaskedexa.roomdatabase.entities.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserEntity) : Long

    @Query("SELECT * FROM user")
    fun getUsersFromDB(): LiveData<List<UserEntity>>
}