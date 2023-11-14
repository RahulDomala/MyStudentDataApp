package com.example.mystudentdata.data

import android.app.AlertDialog
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.mystudentdata.R
import com.example.mystudentdata.model.Student
import kotlinx.android.synthetic.main.popup.view.*

class StudentListAdapter(private val list: ArrayList<Student>,
                         private val context: Context): RecyclerView.Adapter<StudentListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {

        //create a view from out xml file (list_row file)

        val view = LayoutInflater.from(context)
               .inflate(R.layout.list_row, parent, false)

        return ViewHolder(view, context, list)

    }

    override fun getItemCount(): Int {

        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){

        holder.bindView(list[position])      //binding the views with certain position of job in above created list of jobs

    }
        // class made inner to invoke certain functions
    inner class ViewHolder(itemView: View, context: Context, list: ArrayList<Student>): RecyclerView.ViewHolder(itemView) , View.OnClickListener {

        var mContext = context
        var mList = list

        var StudentName = itemView.findViewById(R.id.ListJobName) as TextView
        var FatherName = itemView.findViewById(R.id.ListAssignedBy) as TextView
        var admitDate = itemView.findViewById(R.id.ListDate)    as TextView
        var ContactNumber = itemView.findViewById(R.id.ListAssignedTo) as TextView
        var deleteButton = itemView.findViewById(R.id.DeleteButton) as Button
//        var editButton = itemView.findViewById(R.id.EditButton) as Button

        // this class is used to create out view in OnCreateViewHolder function
        // now we have to transform those views in objects in this class


        fun bindView(job: Student){

            StudentName.text = job.StudentName
            FatherName.text = job.FatherName
            ContactNumber.text = job.ContactNumber
            admitDate.text = job.showHumanDate(System.currentTimeMillis())
            deleteButton.setOnClickListener(this)
//            editButton.setOnClickListener(this) //registering the buttons for click small brackets are used bcz in function below
                                                // is not being implemented bcz the curly brackets are calling the function into its context
        }

        override fun onClick(v: View?) {

            var mPosition: Int = adapterPosition
            var  job = mList[mPosition]


            when(v!!.id) {
                deleteButton.id -> {
                    deleteJob(job.id!!)
                    mList.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)

                }

//                editButton.id -> {
//
//                    editJob(job)
//
//                }
            }

        }

        fun deleteJob(id: Int) {

            var db: StudentDatabaseHandler = StudentDatabaseHandler(mContext)
            db.deletejob(id)

        }

        fun editJob(job: Student) {

            var dialogBuilder: AlertDialog.Builder?
            var dialog: AlertDialog?
            var dbHandler: StudentDatabaseHandler = StudentDatabaseHandler(context)

            var view = LayoutInflater.from(context).inflate(R.layout.popup, null)
            var studentName = view.popenterjobId
            var fatherName = view.popenterallocatorId
            var contactNumber = view.popenteracceptorId
            var saveButton = view.popsaveButtonId

            dialogBuilder = AlertDialog.Builder(context).setView(view)
            dialog = dialogBuilder!!.create()
            dialog?.show()

            saveButton.setOnClickListener {
                var name = StudentName.text.toString().trim()
                var aBy =  FatherName.text.toString().trim()
                var aTo = contactNumber.text.toString().trim()

                if (!TextUtils.isEmpty(name)
                    && !TextUtils.isEmpty(aBy)
                    && !TextUtils.isEmpty(aTo)) {
                    // var chore = Chore()

                    job.StudentName = name
                    job.FatherName = aTo
                    job.ContactNumber = aBy

                    dbHandler!!.updatejob(job)
                    notifyItemChanged(adapterPosition, job)


                    dialog!!.dismiss()


                }
        }
    }
 }
}


