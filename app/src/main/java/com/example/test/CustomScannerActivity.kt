package com.example.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class CustomScannerActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth= Firebase.auth
        database = Firebase.database.reference
        val user = Firebase.auth.currentUser
        setContentView(R.layout.activity_qrscanner)


    }

}