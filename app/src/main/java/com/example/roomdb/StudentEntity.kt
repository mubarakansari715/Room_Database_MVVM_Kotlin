package com.example.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Student")
data class StudentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val course: String,
)
