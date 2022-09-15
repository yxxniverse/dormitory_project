package com.example.test

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_form.*
import java.text.SimpleDateFormat
import java.util.*

class FormInfoActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database : DatabaseReference

    private var calendar = Calendar.getInstance()
    private var year = calendar.get(Calendar.YEAR)
    private var month = calendar.get(Calendar.MONTH)
    private var day = calendar.get(Calendar.DAY_OF_MONTH)
    lateinit var sd :String
    lateinit var ed :String
    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SetText118n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
        auth=Firebase.auth
        database = Firebase.database.reference
        val user = Firebase.auth.currentUser

        user?.let {

            var saveFormInfoBtn = findViewById<Button>(R.id.enter_btn)
            var userInfoView = findViewById<TextView>(R.id.Info_View)
            var destinationEditText = findViewById<EditText>(R.id.states)
            var durationStartTextView = findViewById<TextView>(R.id.date_show1)
            var durationEndTextView = findViewById<TextView>(R.id.date_show2)
            var reasonEditText = findViewById<EditText>(R.id.reason_pwd)
            var agreementCheckBox = findViewById<CheckBox>(R.id.agm_cb)

            saveFormInfoBtn.setOnClickListener {

                    UserFormInfo(
                        destinationEditText.text.toString(),
                        durationStartTextView.text.toString(),
                        durationEndTextView.text.toString(),
                        reasonEditText.text.toString(),
                        agreementCheckBox.text.toString(),
                        user.uid.toString()
                    )


            }

            database.child("users").child(user.uid)
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                    }
                    override fun onDataChange(snapshot: DataSnapshot) {
                        var myName = snapshot.child("name").value.toString()
                        var myStudentId = snapshot.child("studentId").value.toString()
                        var myBirthday = snapshot.child("birthday").value.toString()
                        var myDormName = snapshot.child("dormName").value.toString()
                        var myDormRoom = snapshot.child("dormRoom").value.toString()
                        var myPhoneNumber = snapshot.child("phoneNumber").value.toString()
                        var myEmail = user.email

                        userInfoView.setText("이름:"+myName+"\n"+"학번:"+myStudentId+"\n"+"생년월일:"+myBirthday
                                +"\n"+"이메일:"+myEmail+"\n"+ "전화번호:"+myPhoneNumber+"\n"+"관실 정보:"+myDormName+" "+myDormRoom+"호\n")

                    }
                })
        }

        cal_btn1.setOnClickListener {  //캘린더뷰 만들기
            val datePickerDialog = DatePickerDialog(this, { _, year, month, day ->
                val date_m = "0"+(month+1).toString()
                val date_d = "0"+day.toString()
                sd = year.toString()+(month+1)+day
                date_show1.text =
                    year.toString() + "-" + date_m.substring(date_m.length-2,date_m.length) + "-" + date_d.substring(date_d.length-2,date_d.length)
            }, year, month, day)
            datePickerDialog.show()
        }

        cal_btn2.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this, { _, year, month, day ->
                val date_m = ("0"+(month+1).toString())
                val date_d = "0"+day.toString()
                ed = year.toString()+(month+1)+day
                date_show2.text =
                    year.toString() + "-" + date_m.substring(date_m.length-2,date_m.length) + "-" + date_d.substring(date_d.length-2,date_d.length)
            }, year, month, day)
            datePickerDialog.show()
        }

    }

    fun UserFormInfo(destination:String, durationStart:String, durationEnd:String, reason:String, agreement:String, uId:String){

        var formMap = HashMap<String, String>()
        var agmCheckBox = findViewById<CheckBox>(R.id.agm_cb)

        if(agmCheckBox.isChecked) {
            formMap.put("destination", destination)
            formMap.put("start_date", durationStart)
            formMap.put("end_date", durationEnd)
            formMap.put("reason", reason)
            formMap.put("agreement", agreement)

            database.child("users").child(uId).child("외박신청").push().setValue(formMap)

            Toast.makeText(
                this, "외박 신청이 완료되었습니다.",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }
        else{
            Toast.makeText(
                this, "서약서에 동의해주세요.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}

