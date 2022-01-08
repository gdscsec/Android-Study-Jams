package com.example.infostack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.infostack.R
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast
import android.content.Intent
import android.view.View
import android.widget.Button
import com.example.infostack.studentlogin
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.example.infostack.Studentdashboard
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.firebase.database.DatabaseError
import java.util.ArrayList
import java.util.HashMap

class Studentdashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_studentdashboard)
        val studentName = findViewById<View>(R.id.student_name) as TextView
        val stuid = findViewById<View>(R.id.student_id) as TextView
        val stureg = findViewById<View>(R.id.student_regno) as TextView
        val stuphno = findViewById<View>(R.id.student_phno) as TextView
        val stumail = findViewById<View>(R.id.student_mail) as TextView
        val stuclgname = findViewById<View>(R.id.student_clgname) as TextView
        val stuyear = findViewById<View>(R.id.student_clgyear) as TextView
        val stuclgsem = findViewById<View>(R.id.student_clgsem) as TextView
        val stuschl = findViewById<View>(R.id.student_hsc_school_name) as TextView
        val stuhscyear = findViewById<View>(R.id.student_hsc_school_year) as TextView
        val stuhscboard = findViewById<View>(R.id.student_hsc_school_board) as TextView
        val stusslcschlname = findViewById<View>(R.id.student_slsc_school_name) as TextView
        val stusslcyear = findViewById<View>(R.id.student_slsc_school_year) as TextView
        val stusslcboard = findViewById<View>(R.id.student_slsc_school_board) as TextView
        val studob = findViewById<View>(R.id.student_dob) as TextView
        val studclgdept = findViewById<View>(R.id.student_dept) as TextView
        val stdlogout = findViewById<View>(R.id.logout_student_dashboard) as Button
        stdlogout.setOnClickListener { // click handling code
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(this@Studentdashboard, "Logout Successfully", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@Studentdashboard, studentlogin::class.java))
            finish()
        }
        val databaseReference = FirebaseDatabase.getInstance().getReference("student")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.value as HashMap<String, Any>?
                val hashMap = value!![getDBUserName(FirebaseAuth.getInstance().currentUser.email)] as HashMap<String, Any>?
                val bloodGrp = hashMap!!["blood_group"].toString()
                val cgpa = hashMap["cgpa"].toString()
                val clgName = hashMap["college_name"].toString()
                val clgYr = hashMap["college_year"].toString()
                val dob = hashMap["dob"].toString()
                val dept = hashMap["department"].toString()
                val email = hashMap["email"].toString()
                val hscBoard = hashMap["hsc_board"].toString()
                val hscScl = hashMap["hsc_school"].toString()
                val hscScore = hashMap["hsc_score"].toString()
                val hscYr = hashMap["hsc_year"].toString()
                val id = hashMap["id"].toString()
                val name = hashMap["name"].toString()
                val regNo = hashMap["register_no"].toString()
                val sem = hashMap["semester"].toString()
                val sslcBoard = hashMap["sslc_board"].toString()
                val sslcScl = hashMap["sslc_school"].toString()
                val sslcScore = hashMap["sslc_score"].toString()
                val sslcYr = hashMap["sslc_year"].toString()
                stuclgname.text = clgName
                stuyear.text = clgYr
                studob.text = dob
                studclgdept.text = dept
                stumail.text = email
                stuhscboard.text = hscBoard
                stuschl.text = hscScl
                stuhscyear.text = hscYr
                stuid.text = id
                studentName.text = name
                stureg.text = regNo
                stuclgsem.text = sem
                stusslcboard.text = sslcBoard
                stusslcschlname.text = sslcScl
                stusslcyear.text = sslcYr
                val barChart = findViewById<View>(R.id.barchart) as BarChart
                val entries = ArrayList<BarEntry>()
                entries.add(BarEntry(0f, 0))
                entries.add(BarEntry(sslcScore.toFloat(), 1))
                entries.add(BarEntry(0f, 2))
                entries.add(BarEntry(hscScore.toFloat(), 3))
                entries.add(BarEntry(0f, 4))
                entries.add(BarEntry(cgpa.toFloat(), 5))
                val bardataset = BarDataSet(entries, "Cells")
                val labels = ArrayList<String>()
                labels.add("SSLC")
                labels.add("")
                labels.add("HSC")
                labels.add("")
                labels.add("B.Tech")
                labels.add("")
                val data = BarData(labels, bardataset)
                barChart.data = data // set the data and list of labels into chart
                bardataset.setColors(ColorTemplate.COLORFUL_COLORS)
                barChart.animateY(5000)
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    override fun onBackPressed() {
        finish()
    }

    companion object {
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