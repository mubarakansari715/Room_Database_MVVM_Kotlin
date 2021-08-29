package com.example.roomdb.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdb.StudentEntity
import com.example.roomdb.R

class StudentAdapter(
    val context: Context,
    val listStudent: ArrayList<StudentEntity>,
    val onClickHandler: OnClickHandler
):
    RecyclerView.Adapter<StudentAdapter.StudentViewHolder>()
{
    class StudentViewHolder
        (itemView: View,
         onClickHandler: OnClickHandler,
         listStudent: ArrayList<StudentEntity>
    ):
        RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener{
                onClickHandler.onClick(listStudent[adapterPosition])
            }

            itemView.setOnLongClickListener {
                onClickHandler.onLongPress(listStudent[adapterPosition])
                true
            }
        }

        val textStudentId: TextView = itemView.findViewById(R.id.text_studentid)
        val textStudentName: TextView = itemView.findViewById(R.id.text_studentname)
        val textStudentCourse: TextView = itemView.findViewById(R.id.text_studentcourse)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(context).
        inflate(R.layout.student_layout,parent,false)

        return StudentViewHolder(view,onClickHandler,listStudent)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val studentEntity = listStudent[position]
        holder.textStudentCourse.text = studentEntity.course
        holder.textStudentId.text = studentEntity.id.toString()
        holder.textStudentName.text = studentEntity.name
    }

    override fun getItemCount(): Int {
        return listStudent.size
    }


}
interface OnClickHandler{
    fun onClick(student: StudentEntity)
    fun onLongPress(student: StudentEntity)
}
