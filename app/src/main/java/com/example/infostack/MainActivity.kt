package com.example.infostack

import android.app.Activity
import android.os.Bundle
import com.example.infostack.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.example.infostack.MainActivity
import android.content.Intent
import android.os.Handler
import com.example.infostack.staffdashboard
import com.example.infostack.Studentdashboard
import com.google.firebase.database.DatabaseError
import com.example.infostack.studentlogin
import java.util.HashMap

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Handler().postDelayed({
            if (FirebaseAuth.getInstance().currentUser != null) {
                val email = FirebaseAuth.getInstance().currentUser.email
                val databaseReference = FirebaseDatabase.getInstance().getReference("staff")
                databaseReference.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val value = snapshot.value as HashMap<String, Any>?
                        if (value!!.containsKey(getDBUserName(email))) {
                            startActivity(Intent(this@MainActivity, staffdashboard::class.java))
                            finish()
                            return
                        } else {
                            startActivity(Intent(this@MainActivity, Studentdashboard::class.java))
                            finish()
                            return
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {}
                })
            } else {
                val intent = Intent(this@MainActivity, studentlogin::class.java)
                startActivity(intent)
                finish()
            }
        }, splash_screen.toLong())
    }

    override fun onBackPressed() {
        finish()
    }

    companion object {
        private const val splash_screen = 5000
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