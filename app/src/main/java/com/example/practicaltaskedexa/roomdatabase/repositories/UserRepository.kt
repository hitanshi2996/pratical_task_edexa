package com.example.practicaltaskedexa.roomdatabase.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.practicaltaskedexa.roomdatabase.UserDatabase
import com.example.practicaltaskedexa.roomdatabase.entities.UserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserRepository {

    companion object {

        var userDatabase: UserDatabase? = null
        var userList: LiveData<List<UserEntity>>? = null
        private lateinit var user: LiveData<UserEntity>

        private fun initializeDB(context: Context): UserDatabase {
            return UserDatabase.getDatabaseClient(context)
        }

        fun insertUser(context: Context, user: UserEntity) {

            userDatabase = initializeDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                var id = userDatabase!!.userDao().insertUser(user = user)
                Log.e("user id", id.toString())
            }
        }

        fun getAllUsers(context: Context): LiveData<List<UserEntity>>? {

            userDatabase = initializeDB(context)
            userList = userDatabase!!.userDao().getUsersFromDB()
            return userList
        }

        fun updateUser(context: Context, user: UserEntity) {
            userDatabase = initializeDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                var id = userDatabase!!.userDao().updateUser(user = user)
                Log.e("user id", id.toString())
            }
        }

        fun getSingleUser(context: Context, userID: Int): LiveData<UserEntity> {
            userDatabase = initializeDB(context)
            user = userDatabase!!.userDao().getSingleUserFromDB(userID.toLong())
            return user
        }


    }
}