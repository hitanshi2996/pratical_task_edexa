package com.example.practicaltaskedexa.roomdatabase.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employee")
data class EmployeeEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "city")
    val city: String,

    @ColumnInfo(name = "last_name")
    val last_name: String,

    @ColumnInfo(name = "first_name")
    val first_name: String,

    @ColumnInfo(name = "selected")
    var selected: Boolean = false
)