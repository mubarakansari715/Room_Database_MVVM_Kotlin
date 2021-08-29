package com.example.roomdb.Fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdb.Adapter.OnClickHandler
import com.example.roomdb.Adapter.StudentAdapter
import com.example.roomdb.DataClass.StudentDatabase
import com.example.roomdb.Repository.StudentRepository
import com.example.roomdb.StudentEntity
import com.example.roomdb.R
import com.example.roomdb.StudentViewModel
import com.example.roomdb.ViewModel.StudentViewModelFactory
import com.example.roomdb.databinding.FragmentHomeBinding
import com.example.roomdb.databinding.StudentLayoutEditBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment(), OnClickHandler {
    lateinit var homeBinding: FragmentHomeBinding //Home fragment activity called
    lateinit var studentViewModel: StudentViewModel
    lateinit var bottomSheet: BottomSheetDialog
    lateinit var bindingSheet: StudentLayoutEditBinding //edit layout called
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment

        homeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        val studentDao = StudentDatabase.getDatabase(this.requireActivity()).getStudentDao()
        val studentRepository = StudentRepository(studentDao)
        //viewModel Called
        studentViewModel = ViewModelProvider(requireActivity(),
            StudentViewModelFactory(studentRepository
            )).get(StudentViewModel::class.java)

        homeBinding.studentDataModel = studentViewModel

        //bottomsheet dialog box
        bottomSheet = BottomSheetDialog(requireContext())
        bindingSheet = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.student_layout_edit,
            null,
            false
        )
        bottomSheet.setContentView(bindingSheet.root)

        studentViewModel.getStudentData().observe(requireActivity(), {

            if (bottomSheet.isShowing) {
                bottomSheet.dismiss()
            }
            //set RecycleView
            homeBinding.root.recyclerview_studentdata.apply {
                layoutManager = LinearLayoutManager(requireContext())
                val adpater = StudentAdapter(requireContext(), it as ArrayList<StudentEntity>,
                    this@HomeFragment)
                adapter = adpater
            }
        })

        return homeBinding.root
    }

    override fun onClick(student: StudentEntity) {
        bottomSheet.show()
        studentViewModel.name = student.name
        studentViewModel.course = student.course
        bindingSheet.studentData = studentViewModel
        bindingSheet.sData = student

        bindingSheet.buttonCanceledit.setOnClickListener {
            bottomSheet.dismiss()
        }
    }

    override fun onLongPress(student: StudentEntity) {
        val dialogBuilder = AlertDialog.Builder(requireContext())
            .setTitle("Do you really want to delete ?")
            .setIcon(R.drawable.ic_delete)
            .setMessage("Once click on ok button data will be deleted..")


        dialogBuilder.setPositiveButton("Ok") { dialogInterface, which ->
            studentViewModel.delete(student)
        }

        dialogBuilder.setNegativeButton("Cancel") { dialogInterface, which ->
            dialogInterface.dismiss()
        }

        val alertDialog = dialogBuilder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()

    }


}