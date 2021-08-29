package com.example.roomdb.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.roomdb.StudentEntity

@Dao
interface StudentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(student: StudentEntity)

    @Update
    fun update(student: StudentEntity)

    @Delete
    fun delete(student: StudentEntity)

    @Query("select * from Student Order by id desc")
    fun getStudentData(): LiveData<List<StudentEntity>>
}