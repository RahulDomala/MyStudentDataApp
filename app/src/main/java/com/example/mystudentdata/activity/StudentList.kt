package com.example.mystudentdata.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.mystudentdata.data.StudentListAdapter
import com.example.mystudentdata.R
import com.example.mystudentdata.data.StudentDatabaseHandler
import com.example.mystudentdata.model.Student
import kotlinx.android.synthetic.main.activity_job_list.*
import kotlinx.android.synthetic.main.popup.view.*

class StudentList : AppCompatActivity() {

    private var adapter: StudentListAdapter? = null
    private var joblist: ArrayList<Student>? = null
    private var jobListItems: ArrayList<Student>? = null
    private var dialogBuilder: AlertDialog.Builder? = null
    private var dialog: AlertDialog? = null

    private var layoutManager: RecyclerView.LayoutManager? = null
    var dbhandler: StudentDatabaseHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_list)

        dbhandler = StudentDatabaseHandler(this)

        layoutManager = LinearLayoutManager(this)
        joblist = ArrayList<Student>()
        jobListItems = ArrayList()
        adapter = StudentListAdapter(jobListItems!!, this)


        //setup list = recycler view
        recyclerviewId.layoutManager = layoutManager
        recyclerviewId.adapter = adapter

        // load our jobs
        joblist = dbhandler!!.readJobs()
        joblist!!.reverse()

        for (c in joblist!!.iterator()) {

            val job = Student()
            job.StudentName = c.StudentName
            job.FatherName = "Father Name:  ${c.FatherName}"
            job.ContactNumber = "Contact Number:  ${c.ContactNumber}"
            job.id = c.id
            job.showHumanDate(c.timeAdmit!!)

            jobListItems!!.add(job)
        }

        adapter!!.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.top_menu, menu) //menu object is passed here declared in overriding function
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {  //this function is used to provide and onClickListener to the add menu button

        if (item!!.itemId == R.id.add_menu_button){

            Log.d("chutiyapa", "bht bda")
            createPopupDialog()
        }

        return super.onOptionsItemSelected(item)
    }

    fun createPopupDialog(){  // instantiate dialog builder and dialog

        var view = layoutInflater.inflate(R.layout.popup, null) // this view will have our popup
        var StudentName = view.popenterjobId
        var FatherName = view.popenterallocatorId
        var ContactNumber = view.popenteracceptorId
        var savejob = view.popsaveButtonId

        // instantiating dialog builder
        dialogBuilder = AlertDialog.Builder(this).setView(view)
        dialog  = dialogBuilder!!.create()
        dialog?.show()

        savejob.setOnClickListener {
            if (!TextUtils.isEmpty(StudentName.text.toString().trim())
                && !TextUtils.isEmpty((FatherName.text.toString().trim()))
                && !TextUtils.isEmpty(ContactNumber.text.toString().trim())){

                var job = Student()
                job.StudentName = StudentName.text.toString().trim()
                job.FatherName = FatherName.text.toString().trim()
                job.ContactNumber = ContactNumber.text.toString().trim()


                dbhandler!!.createJob(job)
                dialog!!.dismiss()

                startActivity(Intent(this, StudentList :: class.java))
                finish()

            }
        }


    }
}
