package com.example.practicaltaskedexa.roomdatabase.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.practicaltaskedexa.roomdatabase.entities.UserEntity
import androidx.room.Update


@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserEntity): Long

    @Query("SELECT * FROM user")
    fun getUsersFromDB(): LiveData<List<UserEntity>>

    @Update
    fun updateUser(user: UserEntity?)

    @Query("SELECT * FROM user where id = :userid")
    fun getSingleUserFromDB(userid: Long): LiveData<UserEntity>
}