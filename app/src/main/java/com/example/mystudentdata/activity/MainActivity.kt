package com.example.mystudentdata.activity

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.mystudentdata.R
import com.example.mystudentdata.data.StudentDatabaseHandler
import com.example.mystudentdata.model.Student
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var dbhandler: StudentDatabaseHandler? = null
    var progressBar: ProgressDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = ProgressDialog(this)

        dbhandler = StudentDatabaseHandler(this)

        checkDB()



       saveButton.setOnClickListener {
           progressBar!!.setMessage("saving...")
           progressBar!!.show()

            if (!TextUtils.isEmpty(StuId.text.toString()) && !TextUtils.isEmpty(FatherName.text.toString()) &&
                !TextUtils.isEmpty(PhoneId.text.toString())){

                // save to database

                var job: Student =  Student()
                job.StudentName = StuId.text.toString()
                job.FatherName = PhoneId.text.toString()
                job.ContactNumber = FatherName.text.toString()

                saveTODB(job)
                progressBar!!.cancel()
                startActivity(Intent(this, StudentList:: class.java))



            }
            else {
                Toast.makeText(this, "Please enter the information", Toast.LENGTH_LONG).show()
            }
        }

    }

    fun saveTODB(job: Student){
        dbhandler!!.createJob(job)
    }

    fun checkDB(){ //checking if database has some data then it will redirect user to second job list page
        if (dbhandler!!.getJobsCount() > 0)
            startActivity(Intent(this, StudentList :: class.java))

    }
}
