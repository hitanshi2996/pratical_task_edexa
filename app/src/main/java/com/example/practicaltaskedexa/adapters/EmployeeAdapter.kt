package com.example.practicaltaskedexa.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practicaltaskedexa.databinding.RawEmployeeBinding
import com.example.practicaltaskedexa.roomdatabase.entities.EmployeeEntity
import java.lang.Exception

class EmployeeAdapter(
    private val employeeList: MutableList<EmployeeEntity>
) : RecyclerView.Adapter<EmployeeAdapter.ViewHolder>() {

    lateinit var context: Context

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

        with(holder) {
            with(employeeList[position]) {

                binding.txtEmployeeCity.text = "- " + employeeList[position].city
                binding.txtEmployeeFirstName.text = employeeList[position].first_name
                binding.txtEmployeeLastName.text = employeeList[position].last_name

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