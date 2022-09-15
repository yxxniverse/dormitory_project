package com.example.test

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_form.*
import kotlinx.android.synthetic.main.activity_info.*
import java.util.*
import kotlin.collections.HashMap

class InfoActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database : DatabaseReference
    private var calendar = Calendar.getInstance()
    private var year = calendar.get(Calendar.YEAR)
    private var month = calendar.get(Calendar.MONTH)
    private var day = calendar.get(Calendar.DAY_OF_MONTH)
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        auth=Firebase.auth
        database = Firebase.database.reference
        val user = Firebase.auth.currentUser
        user?.let {
            val email = user.email

            var saveInfoBtn = findViewById<Button>(R.id.join_save)
            var userNameEditText = findViewById<EditText>(R.id.join_name)
            var userGenderRadio = findViewById<RadioGroup>(R.id.join_gender)
            var userGenderChecked = findViewById<RadioButton>(userGenderRadio.checkedRadioButtonId)
            var userStudentIdEditText = findViewById<EditText>(R.id.join_id)
            var userBirthdayTextView = findViewById<TextView>(R.id.join_birthday)
            var userDormNameEditText = findViewById<EditText>(R.id.join_dorm)
            var userDormRoomEditText = findViewById<EditText>(R.id.join_dorm2)
            var userPhoneNumberEditText = findViewById<EditText>(R.id.join_phoneNumber)

            join_birthday_btn.setOnClickListener {  //캘린더뷰 만들기
                val datePickerDialog = DatePickerDialog(this, { _, year, month, day ->
                    join_birthday.text =
                        year.toString() + "-" + (month + 1).toString() + "-" + day.toString()
                }, year, month, day)
                datePickerDialog.show()
            }

            saveInfoBtn.setOnClickListener {
                UserInfo(
                    email.toString(),
                    userNameEditText.text.toString(),

                    userStudentIdEditText.text.toString(),
                    userBirthdayTextView.text.toString(),
                    userDormNameEditText.text.toString(),
                    userDormRoomEditText.text.toString(),
                    userPhoneNumberEditText.text.toString(),
                    user.uid.toString()
                )
            }
        }
    }

    fun UserInfo(email:String, name:String, studentId:String, birthday:String, dormName:String, dormRoom:String, phoneNumber:String, uId:String){

        var userMap = HashMap<String, String>()

        userMap.put("name",name)
        userMap.put("email", email)
        userMap.put("studentId", studentId)
        userMap.put("birthday",birthday)
        userMap.put("dormName", dormName)
        userMap.put("dormRoom", dormRoom)
        userMap.put("phoneNumber", phoneNumber)

        database.child("users").child(uId).setValue(userMap)

        Toast.makeText(
            this, "회원정보가 저장되었습니다.",
            Toast.LENGTH_SHORT
        ).show()
        finish()
    }
}