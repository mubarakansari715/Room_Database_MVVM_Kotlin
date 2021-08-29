package com.example.roomdb

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdb.Repository.StudentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class StudentViewModel(val studentRepository: StudentRepository) : ViewModel() {
    var name: String? = null
    var course: String? = null
    fun getStudentData(): LiveData<List<StudentEntity>> {
        return studentRepository.getStudentData()
    }

    fun insert(student: StudentEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            studentRepository.insert(student)
        }
    }

    fun insert() {
        if (name != null && course != null) {
            if (name!!.isNotEmpty() && course!!.isNotEmpty()) {
                val jobInsert = viewModelScope.launch(Dispatchers.IO) {
                    studentRepository.insert(StudentEntity(0, name!!, course!!))
                }
                runBlocking {
                    jobInsert.join()
                }
                name = ""
                course = ""
                // Toast.makeText(context,"Inserted successfully...", Toast.LENGTH_LONG).show()
            } else {
                //Toast.makeText(context,"Name and course should not be null", Toast.LENGTH_LONG).show()
                Log.d("StudentViewModel", "data is null $name and $course")
            }
        } else {
            //Toast.makeText(context,"Name and course should not be null", Toast.LENGTH_LONG).show()
            Log.d("StudentViewModel", "data is null $name and $course")
        }
    }


    fun update(student: StudentEntity) {
        if (name != null && course != null) {
            if (name!!.isNotEmpty() && course!!.isNotEmpty()) {
                val jobUpdate = viewModelScope.launch(Dispatchers.IO) {
                    studentRepository.update(StudentEntity(student.id, name!!, course!!))
                }
                runBlocking {
                    jobUpdate.join()
                }

                // Toast.makeText(context,"Updated successfully...", Toast.LENGTH_LONG).show()

            } else {
                // Toast.makeText(context,"Name and course should not be null", Toast.LENGTH_LONG).show()
                Log.d("StudentViewModel", "data is null $name and $course")
            }
        } else {
            // Toast.makeText(context,"Name and course should not be null", Toast.LENGTH_LONG).show()
            Log.d("StudentViewModel", "data is null $name and $course")
        }
    }


    fun delete(student: StudentEntity) {
        val jobDelete = viewModelScope.launch(Dispatchers.IO) {
            studentRepository.delete(student)
        }
        runBlocking {
            jobDelete.join()
        }

        // Toast.makeText(context,"Deleted successfully...", Toast.LENGTH_LONG).show()

    }


}
