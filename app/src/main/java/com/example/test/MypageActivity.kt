package com.example.test
import android.os.Bundle
import android.widget.*
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlin.math.log

class MypageActivity : AppCompatActivity() {
    val user = Firebase.auth.currentUser
    private lateinit var auth: FirebaseAuth
    private lateinit var database : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        database = Firebase.database.reference
        setContentView(R.layout.activity_mypage)

        var mpNameText = findViewById<EditText>(R.id.mypage_name)
        var mpStudentId = findViewById<EditText>(R.id.mypage_id)
        var mpBirthday = findViewById<EditText>(R.id.mypage_birthday)
        var mpDormName = findViewById<EditText>(R.id.mypage_dorm)
        var mpDormRoom = findViewById<EditText>(R.id.mypage_dorm2)
        var mpEmail = findViewById<EditText>(R.id.mypage_email)
        var mpPhoneNumber = findViewById<EditText>(R.id.mypage_phone)
        var mpSave = findViewById<Button>(R.id.mypage_save)
        user?.let {
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

                        mpNameText.setText(myName)
                        mpStudentId.setText(myStudentId)
                        mpBirthday.setText(myBirthday)
                        mpDormName.setText(myDormName)
                        mpDormRoom.setText(myDormRoom)
                        mpEmail.setText(myEmail)
                        mpPhoneNumber.setText(myPhoneNumber)
                    }
                })

            mpSave.setOnClickListener {
                UserInfo(
                    mpEmail.text.toString(),
                    mpNameText.text.toString(),
                    mpStudentId.text.toString(),
                    mpBirthday.text.toString(),
                    mpDormName.text.toString(),
                    mpDormRoom.text.toString(),
                    mpPhoneNumber.text.toString(),
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
            this, "회원정보가 수정되었습니다.",
            Toast.LENGTH_SHORT
        ).show()
        finish()
    }
}