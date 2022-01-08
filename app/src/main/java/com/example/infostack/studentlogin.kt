package com.example.infostack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.infostack.R
import android.widget.TextView
import android.content.Intent
import android.view.View
import android.widget.Button
import com.example.infostack.stafflogin
import android.widget.EditText
import com.example.infostack.studentlogin
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.example.infostack.Studentdashboard
import com.google.firebase.database.DatabaseError
import java.util.HashMap
import java.util.regex.Pattern

class studentlogin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_studentlogin)
        val signin = findViewById<View>(R.id.sign_up_txt_std) as TextView
        signin.setOnClickListener { // click handling code
            startActivity(Intent(this@studentlogin, stafflogin::class.java))
            finish()
        }
        val stdlogin = findViewById<View>(R.id.sign_in_btn_std) as Button
        stdlogin.setOnClickListener(View.OnClickListener {
            val mail = findViewById<View>(R.id.user_id_edit_txt_std) as EditText
            val pass = findViewById<View>(R.id.password_edit_txt_std) as EditText
            val userEmail = mail.text.toString().trim { it <= ' ' }
            val userPass = pass.text.toString().trim { it <= ' ' }
            if (!isValidEmail(userEmail)) {
                mail.error = "Email cannot be Empty"
                Toast.makeText(applicationContext, "Enter Valid Email ID", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (userPass == "") {
                pass.error = "Password cannot be Empty"
                Toast.makeText(applicationContext, "Password cannot be Empty", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            val databaseReference = FirebaseDatabase.getInstance().getReference("student")
            databaseReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val value = snapshot.value as HashMap<String, Any>?
                    if (value!!.containsKey(getDBUserName(userEmail))) {
                        FirebaseAuth.getInstance().signInWithEmailAndPassword(userEmail, userPass).addOnCompleteListener(this@studentlogin) { task -> // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful) {
                                Toast.makeText(applicationContext, "Invalid Login Credentials", Toast.LENGTH_SHORT).show()
                            } else {
                                val intent = Intent(this@studentlogin, Studentdashboard::class.java)
                                startActivity(intent)
                                Toast.makeText(applicationContext, "Logged In Successfullty", Toast.LENGTH_SHORT).show()
                                finish()
                            }
                        }
                    } else {
                        Toast.makeText(this@studentlogin, "User Does Not Exist", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })
        })
    }

    override fun onBackPressed() {
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