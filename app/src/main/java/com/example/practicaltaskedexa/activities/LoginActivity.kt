package com.example.practicaltaskedexa.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.practicaltaskedexa.databinding.ActivityLoginBinding
import com.example.practicaltaskedexa.roomdatabase.entities.EmployeeEntity
import com.example.practicaltaskedexa.roomdatabase.entities.UserEntity
import com.example.practicaltaskedexa.roomdatabase.viewmodels.EmployeeViewModel
import com.example.practicaltaskedexa.roomdatabase.viewmodels.UserViewModel
import com.example.practicaltaskedexa.utils.Utils

class LoginActivity : AppCompatActivity() {

    private lateinit var databinding: ActivityLoginBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var employeeViewModel: EmployeeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        databinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(databinding.root)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        employeeViewModel = ViewModelProvider(this)[EmployeeViewModel::class.java]

        employeeViewModel.getEmployeeLiveData(this, this)?.observe(this, Observer {

            fun onChanged(@Nullable employeeList: List<EmployeeEntity>) {

                Log.e("employee", employeeList.size.toString())
                // do something
                /*   categoryList.addAll(category)
                   databinding.rvCategoryList.adapter = mAdapter
                   mAdapter.notifyDataSetChanged()*/
            }

            onChanged(it)

        })

        databinding.txtRegi.setOnClickListener() {
            val intent = Intent(this@LoginActivity, RegistrationActivity::class.java)
            startActivity(intent)
            finish()
        }

        databinding.btnLogin.setOnClickListener() {

            if (checkValidation()) {

                let { it ->
                    userViewModel.getUserFromDB(it.applicationContext)
                        ?.observe(this, Observer { it ->
                            Log.e("user", it.size.toString())
                            it.forEach {
                                if (it.email == databinding.txtIpLoginEmail.text.toString() && it.password == Utils.md5(
                                        databinding.txtIpLoginPassword.text.toString()
                                    )
                                ) {
                                    var user: UserEntity = UserEntity(
                                        it.id,
                                        it.fullName,
                                        it.email,
                                        it.phone,
                                        it.city,
                                        it.gender,
                                        it.password
                                    )

                                    val intent =
                                        Intent(this@LoginActivity, HomeActivity::class.java)
                                    intent.putExtra("userid", user.id)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    Toast.makeText(this, "User not found", Toast.LENGTH_LONG).show()
                                }
                            }
                        })
                }

            }

        }
    }

    private fun checkValidation(): Boolean {

        var added: String = ""

        if (!TextUtils.isEmpty(databinding.txtIpLoginEmail.text.toString()) && TextUtils.isEmpty(
                databinding.txtIpLoginMobile.text.toString()
            )
        ) {
            added = "email"
        }

        if (TextUtils.isEmpty(databinding.txtIpLoginEmail.text.toString()) && !TextUtils.isEmpty(
                databinding.txtIpLoginMobile.text.toString()
            )
        ) {
            added = "phone"
        }

        if (added == "") {
            Toast.makeText(
                this@LoginActivity,
                "Please enter email or phone number",
                Toast.LENGTH_LONG
            ).show()
        }

        if (added == "email" && TextUtils.isEmpty(databinding.txtIpLoginEmail.text.toString())) {
            databinding.txtIpLoginEmail.error = "Please enter email"
            return false
        } else if (added == "email" && !android.util.Patterns.EMAIL_ADDRESS.matcher(databinding.txtIpLoginEmail.text.toString())
                .matches()
        ) {
            databinding.txtIpLoginEmail.error = "Please enter valid email"
            return false
        }

        if (added == "phone" && TextUtils.isEmpty(databinding.txtIpLoginMobile.text.toString())) {
            databinding.txtIpLoginMobile.error = "Please enter phone number"
            return false
        } else if (added == "phone" && databinding.txtIpLoginMobile.text.toString().length > 10) {
            databinding.txtIpLoginMobile.error = "Please enter valid phone number"
            return false
        }

        if (TextUtils.isEmpty(databinding.txtIpLoginPassword.text.toString())) {
            databinding.txtIpLoginPassword.error = "Please enter password"
            return false
        }
        return true
    }
}