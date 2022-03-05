package com.example.practicaltaskedexa.roomdatabase.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.example.practicaltaskedexa.roomdatabase.entities.EmployeeEntity
import com.example.practicaltaskedexa.roomdatabase.entities.UserEntity
import com.example.practicaltaskedexa.roomdatabase.repositories.EmployeeRepository
import com.example.practicaltaskedexa.roomdatabase.repositories.UserRepository

class EmployeeViewModel : ViewModel(){
    var employeeList: LiveData<List<EmployeeEntity>>? = null
    var searchEmplyeeList: LiveData<List<EmployeeEntity>>? = null
    var employeeByCity: LiveData<List<EmployeeEntity>>? = null
    lateinit var employee: LiveData<EmployeeEntity>

    fun insertEmployee(context: Context, employee: EmployeeEntity) {
        EmployeeRepository.insertEmployee(context, employee)
    }

    fun getEmployeeFromDB(context: Context): LiveData<List<EmployeeEntity>>? {
        employeeList = EmployeeRepository.getAllEmployee(context)
        return employeeList
    }

    fun getEmployeeByCity(context: Context, city: String): LiveData<List<EmployeeEntity>>? {
        employeeByCity = EmployeeRepository.getEmployeeFromCity(context, city)
        return employeeByCity
    }

    fun getSearchEmployee(
        context: Context,
        city: String,
        search: String
    ): LiveData<List<EmployeeEntity>>? {
        searchEmplyeeList = EmployeeRepository.getSearchEmployee(context, city, search)
        return searchEmplyeeList
    }

    var employeeLiveData = MutableLiveData<List<EmployeeEntity>>()
    val errorMessage = MutableLiveData<String>()

    fun getEmployeeLiveData(owner: ViewModelStoreOwner, context: Context): MutableLiveData<List<EmployeeEntity>>? {
        employeeLiveData = EmployeeRepository().getAllEmployeeFromAPI(owner,context)
        return employeeLiveData
    }
}