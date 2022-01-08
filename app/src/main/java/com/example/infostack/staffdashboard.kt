package com.example.infostack

import androidx.appcompat.app.AppCompatActivity
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import com.example.infostack.R
import com.example.infostack.MinMaxFilter
import com.google.firebase.auth.FirebaseAuth
import android.content.Intent
import com.example.infostack.stafflogin
import com.example.infostack.staffdashboard
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.example.infostack.Student
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.example.infostack.DatePickerFragment
import android.content.ContentValues
import android.text.InputFilter
import android.util.Log
import android.view.View
import android.widget.*
import androidx.fragment.app.DialogFragment
import java.util.*
import java.util.regex.Pattern

class staffdashboard : AppCompatActivity(), OnDateSetListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_staffdashboard)
        val mark1 = findViewById<EditText>(R.id.cgpa)
        mark1.filters = arrayOf<InputFilter>(MinMaxFilter("1", "100"))
        val mark2 = findViewById<EditText>(R.id.hsc_score)
        mark2.filters = arrayOf<InputFilter>(MinMaxFilter("1", "100"))
        val mark3 = findViewById<EditText>(R.id.sslc_score)
        mark3.filters = arrayOf<InputFilter>(MinMaxFilter("1", "100"))
        val spinner = findViewById<Spinner>(R.id.year_std_hsc)
        val adapter = ArrayAdapter.createFromResource(this,
                R.array.Spinner_items, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        val spinner1 = findViewById<Spinner>(R.id.board_std_hsc)
        val adapter1 = ArrayAdapter.createFromResource(this,
                R.array.board, android.R.layout.simple_spinner_item)
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner1.adapter = adapter1
        val spinner2 = findViewById<Spinner>(R.id.year_std_sslc)
        val adapter3 = ArrayAdapter.createFromResource(this,
                R.array.Spinner_items, android.R.layout.simple_spinner_item)
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner2.adapter = adapter3
        val spinner3 = findViewById<Spinner>(R.id.board_std_sslc)
        val adapter4 = ArrayAdapter.createFromResource(this,
                R.array.board, android.R.layout.simple_spinner_item)
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner3.adapter = adapter4
        val spinner4 = findViewById<Spinner>(R.id.blood_grp_std)
        val adapter5 = ArrayAdapter.createFromResource(this,
                R.array.blood_grp, android.R.layout.simple_spinner_item)
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner4.adapter = adapter5
        val spinner5 = findViewById<Spinner>(R.id.year_std_college)
        val adapter6 = ArrayAdapter.createFromResource(this,
                R.array.Spinner_items, android.R.layout.simple_spinner_item)
        adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner5.adapter = adapter6
        val spinner6 = findViewById<Spinner>(R.id.sem_college)
        val adapter7 = ArrayAdapter.createFromResource(this,
                R.array.sem_college, android.R.layout.simple_spinner_item)
        adapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner6.adapter = adapter7
        val logout = findViewById<View>(R.id.logout_staff_dashboard) as ImageView
        logout.setOnClickListener { // click handling code
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(this@staffdashboard, "Logout Successfully", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@staffdashboard, stafflogin::class.java))
            finish()
        }
        val submit = findViewById<View>(R.id.submit_btn_staff) as Button
        submit.setOnClickListener(View.OnClickListener {
            val studentName = findViewById<View>(R.id.student_name_staffdash) as EditText
            val stuid = findViewById<View>(R.id.student_id_staffdash) as EditText
            val stureg = findViewById<View>(R.id.student_regno_staffdash) as EditText
            val stuphno = findViewById<View>(R.id.student_phno_staffdash) as EditText
            val stumail = findViewById<View>(R.id.student_mail_staffdash) as EditText
            val stuclgname = findViewById<View>(R.id.student_clgname_staffdash) as EditText
            val studeptname = findViewById<View>(R.id.student_dept_staffdash) as EditText
            val stucgpa = findViewById<View>(R.id.cgpa) as EditText
            val stuschl = findViewById<View>(R.id.student_schoolname_staffdash) as EditText
            val stuhscscore = findViewById<View>(R.id.hsc_score) as EditText
            val stusslcschlname = findViewById<View>(R.id.student_sslcschoolname_staffdash) as EditText
            val stusslcscore = findViewById<View>(R.id.sslc_score) as EditText
            val stubloodgrp = findViewById<View>(R.id.blood_grp_std) as Spinner
            val stuclgyr = findViewById<View>(R.id.year_std_college) as Spinner
            val stuclgsem = findViewById<View>(R.id.sem_college) as Spinner
            val stuhscstd = findViewById<View>(R.id.year_std_hsc) as Spinner
            val stuhscboard = findViewById<View>(R.id.board_std_hsc) as Spinner
            val stusslcstd = findViewById<View>(R.id.year_std_sslc) as Spinner
            val stusslcsboard = findViewById<View>(R.id.board_std_sslc) as Spinner
            val dob = findViewById<View>(R.id.dob_std) as TextView
            val stuDob = dob.text.toString().trim { it <= ' ' }
            val strstudentName = studentName.text.toString().trim { it <= ' ' }
            val strstuid = stuid.text.toString().trim { it <= ' ' }
            val strstureg = stureg.text.toString().trim { it <= ' ' }
            val strstuphno = stuphno.text.toString().trim { it <= ' ' }
            val strstumail = stumail.text.toString().trim { it <= ' ' }
            val strstuclgname = stuclgname.text.toString().trim { it <= ' ' }
            val strstudept = studeptname.text.toString().trim { it <= ' ' }
            val strstucgpa = stucgpa.text.toString().trim { it <= ' ' }
            val strstuschl = stuschl.text.toString().trim { it <= ' ' }
            val strstuhscscore = stuhscscore.text.toString().trim { it <= ' ' }
            val strstusslcschlname = stusslcschlname.text.toString().trim { it <= ' ' }
            val strstusslcscore = stusslcscore.text.toString().trim { it <= ' ' }
            val strStuBloodGrp = stubloodgrp.selectedItem.toString()
            val strStuClgYear = stuclgyr.selectedItem.toString()
            val strStuClgSem = stuclgsem.selectedItem.toString()
            val strStuHscStd = stuhscstd.selectedItem.toString()
            val strStuHscBoard = stuhscboard.selectedItem.toString()
            val strStuSslcStd = stusslcstd.selectedItem.toString()
            val strStuSslcBoard = stusslcsboard.selectedItem.toString()
            if (strstudentName == "") {
                studentName.error = "Name cannot be Empty"
                Toast.makeText(applicationContext, "Name cannot be Empty", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (strstuid == "") {
                stuid.error = "Student Id cannot be Empty"
                Toast.makeText(applicationContext, "Student Id cannot be Empty", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (strstureg == "") {
                stureg.error = "Student Reg.No cannot be Empty"
                Toast.makeText(applicationContext, "Student Reg.No cannot be Empty", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (strstuphno == "") {
                stuphno.error = "Student Ph.no cannot be Empty"
                Toast.makeText(applicationContext, "Student Ph.No cannot be Empty", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (strstumail == "") {
                stumail.error = "Student Mail cannot be Empty"
                Toast.makeText(applicationContext, "Student Mail cannot be Empty", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (!isValidEmail(strstumail)) {
                stumail.error = "Enter Valid Email"
                Toast.makeText(applicationContext, "Enter Valid Email", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (!isValidPhone(strstuphno)) {
                stuphno.error = "Enter Valid Phone"
                Toast.makeText(applicationContext, "Enter Valid Phone", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (stuDob == "") {
                dob.error = "Student DOB cannot be Empty"
                Toast.makeText(applicationContext, "Student DOB cannot be Empty", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (strstuclgname == "") {
                stuclgname.error = "Student Clg name cannot be Empty"
                Toast.makeText(applicationContext, "Student Clg name cannot be Empty", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (strstudept == "") {
                studeptname.error = "Student Dept cannot be Empty"
                Toast.makeText(applicationContext, "Student Dept cannot be Empty", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (strstucgpa == "") {
                stucgpa.error = "Student CGPA cannot be Empty"
                Toast.makeText(applicationContext, "Student CGPA cannot be Empty", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (strstuschl == "") {
                stuschl.error = "Student School cannot be Empty"
                Toast.makeText(applicationContext, "Student School cannot be Empty", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (strstuhscscore == "") {
                stuhscscore.error = "Student Score cannot be Empty"
                Toast.makeText(applicationContext, "Student Score cannot be Empty", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (strstusslcschlname == "") {
                stusslcschlname.error = "Student School cannot be Empty"
                Toast.makeText(applicationContext, "Student School cannot be Empty", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (strstusslcscore == "") {
                stusslcscore.error = "Student Score cannot be Empty"
                Toast.makeText(applicationContext, "Student Score cannot be Empty", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (strStuBloodGrp == "BLOOD GROUP") {
                Toast.makeText(applicationContext, "Please Select Blood Group", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (strStuClgYear == "YEAR") {
                Toast.makeText(applicationContext, "Please Select College Year", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (strStuClgSem == "CURRENT SEM") {
                Toast.makeText(applicationContext, "Please Select the Sem", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (strStuHscStd == "YEAR") {
                Toast.makeText(applicationContext, "Please Select HSC Year", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (strStuHscBoard == "BOARD") {
                Toast.makeText(applicationContext, "Please Select HSC Board", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (strStuSslcStd == "YEAR") {
                Toast.makeText(applicationContext, "Please Select SSLC Year", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (strStuSslcBoard == "BOARD") {
                Toast.makeText(applicationContext, "Please Select SSLC Board", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            val databaseReference = FirebaseDatabase.getInstance().getReference("student")
            val st = Student(strstudentName, strstuid, strstureg, strstuphno, strstumail, strstuclgname, strstudept, strstucgpa, strstusslcschlname, strstuschl, strstuhscscore, strstusslcscore, strStuBloodGrp, strStuClgYear, strStuClgSem, strStuHscStd, strStuSslcStd, strStuHscBoard, strStuSslcBoard, stuDob)
            databaseReference.child(getDBUserName(strstumail)).setValue(st)
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(strstumail, strstureg)
                    .addOnCompleteListener(this@staffdashboard) { task -> // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful) {
                            Toast.makeText(this@staffdashboard, "Try Again!",
                                    Toast.LENGTH_SHORT).show()
                        } else {
                        }
                    }
            Toast.makeText(applicationContext, "Student Details Added Sucessfully", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@staffdashboard, staffdashboard::class.java))
            finish()
        })
        val mDisplayDate = findViewById<View>(R.id.dob_std) as TextView
        mDisplayDate.setOnClickListener {
            val datePicker: DialogFragment = DatePickerFragment()
            datePicker.show(supportFragmentManager, "date picker")
        }
    }

    override fun onBackPressed() {
        finish()
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        var month = month
        val c = Calendar.getInstance()
        c[Calendar.YEAR] = year
        c[Calendar.MONTH] = month
        c[Calendar.DAY_OF_MONTH] = dayOfMonth
        month = month + 1
        Log.d(ContentValues.TAG, "onDateSet: dd/mm/yyyy: $dayOfMonth/$month/$year")
        val date = String.format("%02d", dayOfMonth) + "-" + String.format("%02d", month) + "-" + String.format("%02d", year)
        val textView = findViewById<View>(R.id.dob_std) as TextView
        textView.text = date
        val userYear = year
    }

    companion object {
        fun isValidEmail(email: String?): Boolean {
            val emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                    "[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                    "A-Z]{2,7}$"
            val pat = Pattern.compile(emailRegex)
            return if (email == null) false else pat.matcher(email).matches()
        }

        fun isValidPhone(phone: String?): Boolean {
            val regex = "\\d{10}"
            val pattern = Pattern.compile(regex)
            val matcher = pattern.matcher(phone)
            return if (matcher.matches()) {
                true
            } else {
                false
            }
        }

        fun getDBUserName(S: String): String {
            var result = ""
            for (ch in S.toCharArray()) {
                if (ch == '@') {
                    break
                }
                result += ch
            }
            return result
        }
    }
}