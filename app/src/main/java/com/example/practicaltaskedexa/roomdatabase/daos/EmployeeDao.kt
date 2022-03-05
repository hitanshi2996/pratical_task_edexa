package com.example.practicaltaskedexa.roomdatabase.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.practicaltaskedexa.roomdatabase.entities.EmployeeEntity
import com.example.practicaltaskedexa.roomdatabase.entities.UserEntity

@Dao
interface EmployeeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEmployee(employee: EmployeeEntity): Long

    @Query("SELECT * FROM employee")
    fun getEmployees(): LiveData<List<EmployeeEntity>>

    @Query("SELECT * FROM employee WHERE city = :city")
    fun getEmployeeByCity(city: String): LiveData<List<EmployeeEntity>>

    @Query("SELECT * FROM employee WHERE city = :city AND (first_name LIKE '%' || :search || '%' OR last_name LIKE '%' || :search || '%')")
    fun getSearchEmployee(city: String, search: String): LiveData<List<EmployeeEntity>>

    @Query("SELECT * FROM employee WHERE id = :id")
    fun getEmployeeByID(id: Int): LiveData<EmployeeEntity>

    @Query("UPDATE employee SET selected = :selected WHERE id = :id")
    fun setEmployeeByID(id: Int, selected: Boolean)
}