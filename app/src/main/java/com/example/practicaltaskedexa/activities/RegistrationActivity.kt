package com.example.practicaltaskedexa.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.RadioGroup
import androidx.lifecycle.ViewModelProvider
import com.example.practicaltaskedexa.databinding.ActivityRegistrationBinding
import com.example.practicaltaskedexa.roomdatabase.entities.UserEntity
import com.example.practicaltaskedexa.roomdatabase.viewmodels.UserViewModel
import com.example.practicaltaskedexa.utils.Utils

import android.widget.RadioButton
import com.example.practicaltaskedexa.R


class RegistrationActivity : AppCompatActivity() {

    private lateinit var databinding: ActivityRegistrationBinding
    private lateinit var userViewModel: UserViewModel
    private var gender: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        databinding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(databinding.root)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        databinding.btnSubmit.setOnClickListener() {
            if (checkValidation()) {
                saveDataToDataBase()
            }
        }

        databinding.rgbGender.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            gender = rb.text.toString()
        }
    }

    private fun saveDataToDataBase() {

        var user = UserEntity(
            0,
            databinding.txtIpFullName.text.toString(),
            databinding.txtIpEmail.text.toString(),
            databinding.txtIpPhoneNumber.text.toString(),
            databinding.txtIpCity.text.toString(),
            gender,
            Utils.md5(databinding.txtIpPassword.text.toString()).toString()
        )

        userViewModel.insertUser(baseContext, user)

        val intent = Intent(this@RegistrationActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun checkValidation(): Boolean {

        if (TextUtils.isEmpty(databinding.txtIpFullName.text.toString())) {
            databinding.txtIpFullName.error = getString(R.string.enter_full_name)
            return false
        }

        if (TextUtils.isEmpty(databinding.txtIpEmail.text.toString())) {
            databinding.txtIpEmail.error = getString(R.string.enter_email)
            return false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(databinding.txtIpEmail.text.toString())
                .matches()
        ) {
            databinding.txtIpEmail.error = getString(R.string.enter_valid_email)
            return false
        }

        if (TextUtils.isEmpty(databinding.txtIpPhoneNumber.text.toString())) {
            databinding.txtIpPhoneNumber.error = getString(R.string.phone_number)
            return false
        } else if (databinding.txtIpPhoneNumber.text.toString().length > 10) {
            databinding.txtIpPhoneNumber.error = getString(R.string.enter_valid_phone_number)
            return false
        }

        if (TextUtils.isEmpty(databinding.txtIpCity.text.toString())) {
            databinding.txtIpCity.error = getString(R.string.enter_city)
            return false
        }

        if (TextUtils.isEmpty(databinding.txtIpPassword.text.toString())) {
            databinding.txtIpPassword.error = getString(R.string.enter_password)
            return false
        }
        return true
    }
}