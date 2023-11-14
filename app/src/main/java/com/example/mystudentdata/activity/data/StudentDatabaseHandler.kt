package com.example.mystudentdata.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.mystudentdata.model.*
import java.text.DateFormat
import java.util.*

class StudentDatabaseHandler(context: Context):
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        // use SQL to create table
        var CREATE_JOB_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY,"+
                KEY_STUDENT_NAME + " TEXT," +
                KEY_FATHER_NAME + " TEXT," +
                KEY_CONTACT_NUMBER + " TEXT," +
                KEY_ADMIT_TIME + " LONG" +");"
        db?.execSQL(CREATE_JOB_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        // create a new table after deleting the previous using above function
        onCreate(db)
    }

    /* CRUD = create read update delete */

    fun createJob(job: Student) {

        var db: SQLiteDatabase = writableDatabase

        var values: ContentValues = ContentValues()
        values.put(KEY_STUDENT_NAME, job.StudentName)
        values.put(KEY_FATHER_NAME, job.FatherName)
        values.put(KEY_CONTACT_NUMBER, job.ContactNumber)
        values.put(KEY_ADMIT_TIME, System.currentTimeMillis())

        db.insert(TABLE_NAME, null, values)

        Log.d("DEBUG", "SUCCESS")
        db.close()
    }

    fun readAJob(id: Int): Student {

        var db: SQLiteDatabase = writableDatabase
        var cursor: Cursor = db.query(
            TABLE_NAME, arrayOf(
                KEY_ID, KEY_STUDENT_NAME, KEY_FATHER_NAME,
                KEY_CONTACT_NUMBER, KEY_ADMIT_TIME
            ), KEY_ID + "=?", arrayOf(id.toString()),
            null, null, null, null
        )

        if (cursor != null)
            cursor.moveToFirst()

        var job = Student()

        job.id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
        job.StudentName = cursor.getString(cursor.getColumnIndex(KEY_STUDENT_NAME))
        job.FatherName = cursor.getString(cursor.getColumnIndex(KEY_FATHER_NAME))
        job.ContactNumber = cursor.getString(cursor.getColumnIndex(KEY_CONTACT_NUMBER))
        job.timeAdmit = cursor.getLong(cursor.getColumnIndex(KEY_ADMIT_TIME))

        var dateFormat: java.text.DateFormat = DateFormat.getDateInstance()
        var formatteddate = dateFormat.format(
            Date(
                cursor.getLong
                    (cursor.getColumnIndex(KEY_ADMIT_TIME))
            ).time
        )

        return job

    }

    fun readJobs(): ArrayList<Student> {


        var db: SQLiteDatabase = readableDatabase
        var list: ArrayList<Student> = ArrayList()

        //Select all chores from table
        var selectAll = "SELECT * FROM " + TABLE_NAME

        var cursor: Cursor = db.rawQuery(selectAll, null)

        //loop through our chores
        if (cursor.moveToFirst()) {
            do {
                var job = Student()

                job.id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                job.StudentName = cursor.getString(cursor.getColumnIndex(KEY_STUDENT_NAME))
                job.FatherName = cursor.getString(cursor.getColumnIndex(KEY_FATHER_NAME))
                job.timeAdmit = cursor.getLong(cursor.getColumnIndex(KEY_ADMIT_TIME))
                job.ContactNumber = cursor.getString(cursor.getColumnIndex(KEY_CONTACT_NUMBER))

                list.add(job)

            }while (cursor.moveToNext())
        }


        return list

    }

    fun updatejob(job: Student): Int {
        var db: SQLiteDatabase = writableDatabase

        var values: ContentValues = ContentValues()
        values.put(KEY_STUDENT_NAME, job.StudentName)
        values.put(KEY_FATHER_NAME, job.FatherName)
        values.put(KEY_CONTACT_NUMBER, job.ContactNumber)
        values.put(KEY_ADMIT_TIME, System.currentTimeMillis())

        //update a row
        return db.update(TABLE_NAME, values, KEY_ID + "=?", arrayOf(job.id.toString()))
    }

    fun deletejob(id: Int) {
        var db: SQLiteDatabase = writableDatabase
        db.delete(TABLE_NAME, KEY_ID + "=?", arrayOf(id.toString()))

        db.close()
    }

    fun getJobsCount(): Int {
        var db: SQLiteDatabase = readableDatabase
        var countQuery = "SELECT * FROM " + TABLE_NAME
        var cursor: Cursor = db.rawQuery(countQuery, null)

        return cursor.count
    }
}