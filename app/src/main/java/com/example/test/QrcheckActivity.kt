package com.example.test

import android.R.attr
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.zxing.integration.android.IntentIntegrator
import android.R.attr.data
import com.google.firebase.database.*

import com.google.zxing.integration.android.IntentResult
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class QrcheckActivity : AppCompatActivity() {
    val user = Firebase.auth.currentUser
    private lateinit var auth: FirebaseAuth
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkin)
        auth=Firebase.auth
        database = Firebase.database.reference

        var scannerBtn = findViewById<Button>(R.id.scanner_btn)
        scannerBtn.setOnClickListener {
            initQRcodeScanner()
        }
    }
    private fun initInfo(DormInfo:String){
        var checkInNameTextView = findViewById<TextView>(R.id.checkIn_name)
        var checkInDormTextView = findViewById<TextView>(R.id.checkIn_dorm)
        var checkInIdTextView = findViewById<TextView>(R.id.checkIn_sutdentId)
        var checkInDateTextView = findViewById<TextView>(R.id.checkIn_date)
        user?.let {
            database.child("users").child(user.uid)
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                    }
                    override fun onDataChange(snapshot: DataSnapshot) {
                        var checkInName = snapshot.child("name").value.toString()
                        var checkInStudentId = snapshot.child("studentId").value.toString()
                        val now:Long = System.currentTimeMillis()
                        val date = Date(now)
                        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale("ko","KR"))
                        val timeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                        val stringDate = dateFormat.format(date)
                        val stringTime = timeFormat.format(date)
                        database.child("users").child(user.uid).child("checkIn").child(stringDate).setValue("DormInfo")
                        checkInNameTextView.setText(checkInName)
                        checkInDormTextView.setText(DormInfo)
                        checkInIdTextView.setText(checkInStudentId)
                        checkInDateTextView.setText(stringTime)
                    }
                })
        }
    }
    private fun initQRcodeScanner(){
        val integrator  = IntentIntegrator(this)
        integrator.setBeepEnabled(false)
        integrator.setOrientationLocked(true)
        integrator.setPrompt("QR코드를 스캔해주세요.")
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result : IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if(result !=null) {
            if(result.contents == null) {
                // qr코드에 주소가 없거나, 뒤로가기 클릭 시
                Toast.makeText(
                    this, "QR인증이 취소되었습니다.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            } else {
                //qr코드에 주소가 있을때
                var obj = JSONObject(result.getContents())
                var DormInfo = obj.getString("관실정보")
                initInfo(DormInfo)
                Toast.makeText(
                    this, "체크인이 완료되었습니다.",
                    Toast.LENGTH_SHORT
                ).show()

            }
        }else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}