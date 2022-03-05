package com.example.practicaltaskedexa.roomdatabase.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.practicaltaskedexa.api.APIInterface
import com.example.practicaltaskedexa.api.RetrofitService
import com.example.practicaltaskedexa.roomdatabase.UserDatabase
import com.example.practicaltaskedexa.roomdatabase.entities.EmployeeEntity
import com.example.practicaltaskedexa.roomdatabase.entities.UserEntity
import com.example.practicaltaskedexa.roomdatabase.viewmodels.EmployeeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmployeeRepository {
    companion object {

        var userDatabase: UserDatabase? = null
        var employeeList: LiveData<List<EmployeeEntity>>? = null
        var searchEmployeeList: LiveData<List<EmployeeEntity>>? = null

        var employeeByCity: LiveData<List<EmployeeEntity>>? = null
        var employeeListAPI = MutableLiveData<List<EmployeeEntity>>()

        private lateinit var user: LiveData<UserEntity>

        private lateinit var employeeViewModel: EmployeeViewModel

        private fun initializeDB(context: Context): UserDatabase {
            return UserDatabase.getDatabaseClient(context)
        }

        fun insertEmployee(context: Context, employee: EmployeeEntity) {

            userDatabase = initializeDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                var id = userDatabase!!.employeeDao().insertEmployee(employee)
                Log.e("employee id", id.toString())
            }
        }

        fun getAllEmployee(context: Context): LiveData<List<EmployeeEntity>>? {

            userDatabase = initializeDB(context)
            employeeList = userDatabase!!.employeeDao().getEmployees()
            return employeeList
        }

        fun getEmployeeFromCity(context: Context, city: String): LiveData<List<EmployeeEntity>>? {

            userDatabase = initializeDB(context)
            employeeByCity = userDatabase!!.employeeDao().getEmployeeByCity(city)
            return employeeByCity
        }

        fun getSearchEmployee(
            context: Context,
            city: String,
            search: String
        ): LiveData<List<EmployeeEntity>>? {

            userDatabase = initializeDB(context)
            searchEmployeeList = userDatabase!!.employeeDao().getSearchEmployee(city, search)
            return searchEmployeeList
        }

    }

    fun getAllEmployeeFromAPI(
        owner: ViewModelStoreOwner,
        context: Context
    ): MutableLiveData<List<EmployeeEntity>> {
        employeeViewModel = ViewModelProvider(owner)[EmployeeViewModel::class.java]
        val call = RetrofitService.getInstance().create(APIInterface::class.java).getEmployee()

        call.enqueue(object : Callback<ArrayList<EmployeeEntity>> {
            override fun onResponse(
                call: Call<ArrayList<EmployeeEntity>>,
                response: Response<ArrayList<EmployeeEntity>>
            ) {
                val body = response.body()
                if (body != null) {
                    val employeeListResponse = mutableListOf<EmployeeEntity>()
                    body.forEach {

                        employeeListResponse.add(
                            EmployeeEntity(
                                it.id,
                                it.city,
                                it.last_name,
                                it.first_name
                            )
                        )
                        employeeViewModel.insertEmployee(context = context, it)
                    }

                    employeeListAPI.value = employeeListResponse
                }
            }

            override fun onFailure(call: Call<ArrayList<EmployeeEntity>>, t: Throwable) {

            }

        })

        return employeeListAPI
    }
}