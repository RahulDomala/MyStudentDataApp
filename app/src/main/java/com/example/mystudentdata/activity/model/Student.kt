package com.example.mystudentdata.model

import java.text.DateFormat
import java.util.*

class Student() {

    var StudentName: String? = null
    var FatherName: String? = null
    var ContactNumber: String? = null
    var timeAdmit: Long? = null
    var id: Int? = null


    constructor(jobName: String, assignedBy: String, assignedTo: String,
                timeAssigned: Long, id: Int): this() {
        
        this.StudentName = StudentName
        this.FatherName = FatherName
        this.ContactNumber = ContactNumber
        this.timeAdmit = timeAdmit
        this.id = id


    }
    fun showHumanDate(timeAssigned: Long): String {

        var dateFormat: java.text.DateFormat = DateFormat.getDateInstance()
        var formattedDate: String = dateFormat.format(Date(timeAssigned).time)

        return "created on  ${formattedDate}"
    }




    }