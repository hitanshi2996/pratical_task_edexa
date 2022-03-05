package com.example.practicaltaskedexa.fragments

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practicaltaskedexa.activities.HomeActivity
import com.example.practicaltaskedexa.adapters.EmployeeAdapter
import com.example.practicaltaskedexa.databinding.EmploeeFragmentBinding
import com.example.practicaltaskedexa.roomdatabase.entities.EmployeeEntity
import com.example.practicaltaskedexa.roomdatabase.entities.UserEntity
import com.example.practicaltaskedexa.roomdatabase.viewmodels.EmployeeViewModel
import com.example.practicaltaskedexa.utils.Utils

class FragmentEmployee(city: String) : Fragment() {

    private lateinit var binding: EmploeeFragmentBinding
    private lateinit var employeeViewModel: EmployeeViewModel
    private lateinit var employeeEntity: EmployeeEntity
    private lateinit var employeeList: MutableList<EmployeeEntity>
    lateinit var mAdapter: EmployeeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = EmploeeFragmentBinding.inflate(inflater, container, false)

        employeeViewModel = ViewModelProvider(this)[EmployeeViewModel::class.java]
        binding.rvEmployee.layoutManager = LinearLayoutManager(requireContext())

        var city: String = arguments?.getString("city").toString()

        if (city == "") {
            let { it ->
                employeeViewModel.getEmployeeFromDB(requireContext())
                    ?.observe(viewLifecycleOwner, Observer { it ->
                        Log.e("emplyee", it.size.toString())
                        employeeList = mutableListOf<EmployeeEntity>()
                        employeeList.addAll(it)
                        mAdapter = EmployeeAdapter(employeeList)
                        binding.rvEmployee.adapter = mAdapter
                        mAdapter.notifyDataSetChanged()
                    })
            }
        } else {
            let { it ->
                employeeViewModel.getEmployeeByCity(requireContext(), city)
                    ?.observe(viewLifecycleOwner, Observer { it ->
                        Log.e("emplyee", it.size.toString())
                        employeeList = mutableListOf<EmployeeEntity>()
                        employeeList.addAll(it)
                        mAdapter = EmployeeAdapter(employeeList)
                        binding.rvEmployee.adapter = mAdapter
                        mAdapter.notifyDataSetChanged()
                    })
            }
        }

        binding.edtSearchEmployee.afterTextChanged {
            if (city == "") {
                let {
                    employeeViewModel.getSearchEmployeeAll(
                        requireContext(),
                        binding.edtSearchEmployee.text.toString()
                    )
                        ?.observe(viewLifecycleOwner, Observer {
                            Log.e("secarch employee", it.size.toString())
                            employeeList = mutableListOf<EmployeeEntity>()
                            employeeList.addAll(it)
                            mAdapter = EmployeeAdapter(employeeList)
                            binding.rvEmployee.adapter = mAdapter
                            mAdapter.notifyDataSetChanged()
                        })
                }
            } else {
                let {
                    employeeViewModel.getSearchEmployee(
                        requireContext(),
                        city,
                        binding.edtSearchEmployee.text.toString()
                    )
                        ?.observe(viewLifecycleOwner, Observer {
                            Log.e("secarch employee", it.size.toString())
                            employeeList = mutableListOf<EmployeeEntity>()
                            employeeList.addAll(it)
                            mAdapter = EmployeeAdapter(employeeList)
                            binding.rvEmployee.adapter = mAdapter
                            mAdapter.notifyDataSetChanged()
                        })
                }
            }
        }


        return binding.root
    }

    fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                afterTextChanged.invoke(s.toString())
            }

            override fun afterTextChanged(editable: Editable?) {

            }
        })
    }

}