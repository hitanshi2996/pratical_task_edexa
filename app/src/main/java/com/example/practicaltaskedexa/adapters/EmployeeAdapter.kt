package com.example.practicaltaskedexa.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practicaltaskedexa.databinding.RawEmployeeBinding
import com.example.practicaltaskedexa.roomdatabase.entities.EmployeeEntity
import android.widget.Toast

import android.view.View.OnLongClickListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.practicaltaskedexa.roomdatabase.viewmodels.EmployeeViewModel


class EmployeeAdapter(
    private val employeeList: MutableList<EmployeeEntity>
) : RecyclerView.Adapter<EmployeeAdapter.ViewHolder>() {

    lateinit var context: Context
    private lateinit var employeeViewModel: EmployeeViewModel

    companion object {

    }

    inner class ViewHolder(val binding: RawEmployeeBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RawEmployeeBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // employeeViewModel = ViewModelProvider(context)[EmployeeViewModel::class.java]
        with(holder) {
            with(employeeList[position]) {

                binding.txtEmployeeCity.text = "- " + employeeList[position].city
                binding.txtEmployeeFirstName.text = employeeList[position].first_name
                binding.txtEmployeeLastName.text = employeeList[position].last_name
                if (employeeList[position].selected) {
                    binding.llRawEmployee.setBackgroundColor(context.resources.getColor(android.R.color.holo_blue_light))
                }
                binding.llRawEmployee.setOnLongClickListener(OnLongClickListener {

                    /*let {
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
                    }*/
                    if (!employeeList[position].selected) {
                        employeeList[position].selected = true
                        binding.llRawEmployee.setBackgroundColor(context.resources.getColor(android.R.color.holo_blue_light))
                    } else {
                        binding.llRawEmployee.setBackgroundColor(context.resources.getColor(android.R.color.white))
                    }

                    true
                })
            }
        }
    }

    override fun getItemCount(): Int {
        return employeeList.size
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }


    override fun getItemViewType(position: Int): Int {
        return position
    }
}