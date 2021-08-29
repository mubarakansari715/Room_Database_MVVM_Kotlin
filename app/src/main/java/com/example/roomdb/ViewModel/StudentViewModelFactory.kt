package com.example.roomdb.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.roomdb.Repository.StudentRepository
import com.example.roomdb.StudentViewModel

class StudentViewModelFactory(private val studentRepository: StudentRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return StudentViewModel(studentRepository) as T

    }
}