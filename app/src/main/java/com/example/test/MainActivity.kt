package com.example.test

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    val user = Firebase.auth.currentUser
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calendar)
        auth = Firebase.auth
        database = Firebase.database.reference
        var logoutBtn = findViewById<Button>(R.id.logout_btn)
        var mypageBtn = findViewById<Button>(R.id.mypage_btn)
        var overnightBtn = findViewById<Button>(R.id.overnight_submission_btn)
        var editBtn = findViewById<Button>(R.id.submission_list_btn)
        var mainQrBtn = findViewById<Button>(R.id.main_qr_btn)
        var mainSubBtn = findViewById<Button>(R.id.main_submission_btn)

        var qrBtn = findViewById<Button>(R.id.qr_btn)

        user?.let {
            database.child("users").child(user.uid)
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {

                        var menuNameView = findViewById<TextView>(R.id.userNameView)
                        var menuDormView = findViewById<TextView>(R.id.dormNameView)

                        var CurrentUserName = snapshot.child("name").value.toString()
                        var CurrentUserDorm = snapshot.child("dormName").value.toString()

                        menuNameView.setText(CurrentUserName)
                        menuDormView.setText(CurrentUserDorm)
                    }
                })
        }

        logoutBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            auth?.signOut()
            Toast.makeText(
                baseContext, "로그아웃 되었습니다",
                Toast.LENGTH_SHORT
            ).show()
        }
        mypageBtn.setOnClickListener {
            startActivity(Intent(this, MypageActivity::class.java))
        }
        overnightBtn.setOnClickListener {
            startActivity(Intent(this, FormInfoActivity::class.java))
        }
        editBtn.setOnClickListener {
            startActivity(Intent(this, EditActivity::class.java))
        }
        qrBtn.setOnClickListener {
            startActivity(Intent(this, QrcheckActivity::class.java))
        }
        mainQrBtn.setOnClickListener {
            startActivity(Intent(this, QrcheckActivity::class.java))
        }
        mainSubBtn.setOnClickListener {
            startActivity(Intent(this, FormInfoActivity::class.java))
        }

    }
}