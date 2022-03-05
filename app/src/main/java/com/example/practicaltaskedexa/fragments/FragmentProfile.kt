package com.example.practicaltaskedexa.fragments

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.practicaltaskedexa.R
import com.example.practicaltaskedexa.activities.HomeActivity
import com.example.practicaltaskedexa.databinding.ProfileFragmentBinding
import com.example.practicaltaskedexa.roomdatabase.entities.UserEntity
import com.example.practicaltaskedexa.roomdatabase.viewmodels.UserViewModel
import com.example.practicaltaskedexa.utils.Utils

class FragmentProfile : Fragment() {

    private lateinit var binding: ProfileFragmentBinding
    private lateinit var userViewModel: UserViewModel
    private var gender: String = ""
    private lateinit var dbUser: UserEntity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProfileFragmentBinding.inflate(inflater, container, false)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        val bundle = arguments
        val userID = bundle!!.getInt("userid")

        for (index in 0 until binding.rgbProfileGender.childCount)
            binding.rgbProfileGender.getChildAt(index).isEnabled = false


        let {
            userViewModel.getSingleUser(requireContext(), userID)
                ?.observe(requireActivity(), Observer {
                    dbUser = it
                    Log.e("user", it.city)
                    binding.txtIpProfileCity.setText(it.city)
                    binding.txtIpProfileFullName.setText(it.fullName)
                    binding.txtIpProfileEmail.setText(it.email)
                    binding.txtIpProfilePhoneNumber.setText(it.phone)
                })
        }

        val user: LiveData<UserEntity> = userViewModel.getSingleUser(requireContext(), userID)

        binding.btnProfileEdit.setOnClickListener() {
            for (index in 0 until binding.rgbProfileGender.childCount) {
                binding.rgbProfileGender.getChildAt(index).isEnabled = true
            }
            binding.txtIpProfileCity.isEnabled = true
            binding.txtIpProfileCity.isFocusable = true
            binding.txtIpProfileCity.isFocusableInTouchMode = true

            binding.txtIpProfileFullName.isEnabled = true
            binding.txtIpProfileFullName.isFocusable = true
            binding.txtIpProfileFullName.isFocusableInTouchMode = true

            binding.txtIpProfilePassword.isEnabled = true
            binding.txtIpProfilePassword.isFocusable = true
            binding.txtIpProfilePassword.isFocusableInTouchMode = true

            binding.btnProfileEdit.visibility = View.GONE
            binding.btnProfileSubmit.visibility = View.VISIBLE

        }

        binding.btnProfileSubmit.setOnClickListener() {

            var pwd = binding.txtIpProfilePassword.text.toString()
            var finalPwd = dbUser.password

            if (!TextUtils.isEmpty(pwd)) {
                finalPwd = Utils.md5(pwd).toString()
            }

            if (checkValidation()) {
                var user: UserEntity = UserEntity(
                    userID,
                    binding.txtIpProfileFullName.text.toString(),
                    binding.txtIpProfileEmail.text.toString(),
                    binding.txtIpProfilePhoneNumber.text.toString(),
                    binding.txtIpProfileCity.text.toString(),
                    gender,
                    finalPwd
                )
                userViewModel.updateUser(requireContext(), user)

                binding.txtIpProfileCity.isEnabled = false
                binding.txtIpProfileCity.isFocusable = false
                binding.txtIpProfileCity.isFocusableInTouchMode = false

                binding.txtIpProfileFullName.isEnabled = false
                binding.txtIpProfileFullName.isFocusable = false
                binding.txtIpProfileFullName.isFocusableInTouchMode = false

                binding.txtIpProfilePassword.isEnabled = false
                binding.txtIpProfilePassword.isFocusable = false
                binding.txtIpProfilePassword.isFocusableInTouchMode = false

                binding.btnProfileEdit.visibility = View.VISIBLE
                binding.btnProfileSubmit.visibility = View.GONE
            }
        }

        binding.rgbProfileGender.setOnCheckedChangeListener { group, checkedId ->
            val rb = binding.root.findViewById<View>(checkedId) as RadioButton
            gender = rb.text.toString()
        }
        return binding.root
    }

    private fun checkValidation(): Boolean {

        if (TextUtils.isEmpty(binding.txtIpProfileFullName.text.toString())) {
            binding.txtIpProfileFullName.error = getString(R.string.enter_full_name)
            return false
        }

        if (TextUtils.isEmpty(binding.txtIpProfileCity.text.toString())) {
            binding.txtIpProfileCity.error = getString(R.string.enter_city)
            return false
        }

        return true
    }
}