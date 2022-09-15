package com.example.test

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class JoinActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        database = Firebase.database.reference
        setContentView(R.layout.activity_join)

        var joinBtn = findViewById<Button>(R.id.join_btn)
        var joinEmail = findViewById<EditText>(R.id.join_email)
        var joinPwd = findViewById<EditText>(R.id.join_pwd)
        var joinPwdChk = findViewById<EditText>(R.id.join_pwdchk)

        joinBtn.setOnClickListener {
            createAccount(joinEmail.text.toString(),joinPwd.text.toString(),joinPwdChk.text.toString())
        }
    }

    private fun createAccount(email:String, password:String, passwordcheck:String){
        if(password != passwordcheck){
            Toast.makeText(
                this,"비밀번호가 일치하지 않습니다",
                Toast.LENGTH_SHORT
            ).show()
        }
        else if (email.isNotEmpty() && password.isNotEmpty()){
            auth?.createUserWithEmailAndPassword(email, password)
                ?.addOnCompleteListener(this){ task->
                    if(task.isSuccessful){

                        Toast.makeText(
                            this,"계정 생성 완료",
                            Toast.LENGTH_SHORT
                        ).show()
                        moveInfoPage(auth?.currentUser)
                        finish()
                    } else {
                        Toast.makeText(
                            this, "계정 생성 실패",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }

    fun moveInfoPage(user: FirebaseUser?){
        if( user!= null){
            startActivity(Intent(this,InfoActivity::class.java))
            finish()
        }
    }
}