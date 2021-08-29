package com.example.roomdb.Repository

import androidx.lifecycle.LiveData
import com.example.roomdb.Dao.StudentDao
import com.example.roomdb.StudentEntity

class StudentRepository(private val studentDao: StudentDao) {

    fun getStudentData():LiveData<List<StudentEntity>>{
        return studentDao.getStudentData()
    }

    suspend fun insert(student: StudentEntity){
        studentDao.insert(student)
    }

    suspend fun update(student: StudentEntity){
        studentDao.update(student)
    }

    suspend fun delete(student: StudentEntity){
        studentDao.delete(student)
    }
}