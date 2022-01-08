package com.example.infostack

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.auth.FirebaseAuth
import android.os.Bundle
import com.example.infostack.R
import android.widget.EditText
import android.widget.Toast
import com.example.infostack.staffregister
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.example.infostack.Staff
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.example.infostack.stafflogin
import java.util.regex.Pattern

class staffregister : AppCompatActivity() {
    var firebaseDatabase: FirebaseDatabase? = null
    var databaseReference: DatabaseReference? = null
    private val auth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_staffregister)
        val button = findViewById<View>(R.id.register_btn_staff) as Button
        button.setOnClickListener(View.OnClickListener {
            val collegeName = findViewById<View>(R.id.college_staff_reg) as EditText
            val department = findViewById<View>(R.id.dept_staff_reg) as EditText
            val email = findViewById<View>(R.id.mail_id_staff_reg) as EditText
            val pass = findViewById<View>(R.id.password_staff_reg) as EditText
            val conPass = findViewById<View>(R.id.confirm_password_staff_reg) as EditText
            val name = findViewById<View>(R.id.name_staff_reg) as EditText
            val userEmail = email.text.toString().trim { it <= ' ' }
            val userName = name.text.toString().trim { it <= ' ' }
            val userPass = pass.text.toString().trim { it <= ' ' }
            val userConPass = conPass.text.toString().trim { it <= ' ' }
            val userdept = department.text.toString().trim { it <= ' ' }
            val userclgname = collegeName.text.toString().trim { it <= ' ' }
            if (userName == "") {
                name.error = "Name cannot be Empty"
                Toast.makeText(applicationContext, "Name cannot be Empty", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (userEmail == "") {
                email.error = "Email cannot be Empty"
                Toast.makeText(applicationContext, "Email cannot be Empty", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (userPass == "") {
                pass.error = "Password cannot be Empty"
                Toast.makeText(applicationContext, "Password cannot be Empty", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (userConPass == "") {
                conPass.error = "Password cannot be Empty"
                Toast.makeText(applicationContext, "Confirm Password cannot be Empty", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (userPass.length <= 6) {
                pass.error = "Choose a Strong Password"
                Toast.makeText(applicationContext, "Choose a Strong Password", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (userdept == "") {
                department.error = "Department cannot be Empty"
                Toast.makeText(applicationContext, "Department cannot be Empty", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (userclgname == "") {
                collegeName.error = "College Name cannot be Empty"
                Toast.makeText(applicationContext, "College Name cannot be Empty", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (!isValidEmail(userEmail)) {
                email.error = "Enter Valid Email ID"
                Toast.makeText(applicationContext, "Enter Valid Email ID", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (userPass != userConPass) {
                conPass.error = "Password must be same"
                Toast.makeText(applicationContext, "Password must be same", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(userEmail, userPass)
                    .addOnCompleteListener(this@staffregister) { task -> // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful) {
                            Toast.makeText(this@staffregister, "Try Again!",
                                    Toast.LENGTH_SHORT).show()
                        } else {
                            val databaseReference = FirebaseDatabase.getInstance().getReference("staff")
                            val detail = Staff(userName, userEmail, userclgname, userdept)
                            databaseReference.child(getDBUserName(userEmail)).setValue(detail)
                            Toast.makeText(applicationContext, "Registration Successful!", Toast.LENGTH_SHORT).show()
                            Log.d("bvfhjv", detail.toString())
                            startActivity(Intent(this@staffregister, stafflogin::class.java))
                            finish()
                        }
                    }
        })
        val backarrowreg = findViewById<View>(R.id.back_arrow_staff_reg) as ImageView
        backarrowreg.setOnClickListener { // click handling code
            startActivity(Intent(this@staffregister, stafflogin::class.java))
            finish()
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this@staffregister, stafflogin::class.java))
        finish()
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