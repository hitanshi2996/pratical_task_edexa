package com.example.practicaltaskedexa.roomdatabase.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.practicaltaskedexa.roomdatabase.entities.UserEntity
import com.example.practicaltaskedexa.roomdatabase.repositories.UserRepository

class UserViewModel : ViewModel() {
    var userList: LiveData<List<UserEntity>>? = null
    lateinit var user: LiveData<UserEntity>

    fun insertUser(context: Context, user: UserEntity) {
        UserRepository.insertUser(context, user)
    }

    fun getUserFromDB(context: Context): LiveData<List<UserEntity>>? {
        userList = UserRepository.getAllUsers(context)
        return userList
    }

    fun updateUser(context: Context, user: UserEntity) {
        UserRepository.updateUser(context, user)
    }

    fun getSingleUser(context: Context, userId: Int): LiveData<UserEntity> {
        user = UserRepository.getSingleUser(context, userId)
        return user
    }
}