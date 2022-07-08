package com.example.intercomapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {

    private lateinit var signIn: Button
    private lateinit var backLogin: Button
    private lateinit var nameLogin: EditText
    private lateinit var pinLogin: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signIn = findViewById(R.id.sign_in)
        backLogin = findViewById(R.id.back_login)
        nameLogin = findViewById(R.id.name_login)
        pinLogin = findViewById(R.id.pin_login)

        //setting up database variables
        var helper = DatabaseHelper(applicationContext)
        var db = helper.readableDatabase

        //when sign in button is clicked check if data entered matches any record in database
        signIn.setOnClickListener {
            var args = listOf<String>(nameLogin.text.toString(),pinLogin.text.toString()).toTypedArray()
            var rs = db.rawQuery("SELECT * FROM USERS WHERE name = ? AND pin = ?",args)
            hideMyKeyboard()
            if(rs.moveToNext()){
                Toast.makeText(applicationContext,"Welcome!", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(applicationContext,"Wrong Password or Username", Toast.LENGTH_LONG).show()
            }
        }

        //when back button(login) is clicked show home screen and hide rest
        backLogin.setOnClickListener {
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    //function to hide keyboard after button is clicked
    private fun hideMyKeyboard(){
        val view = this.currentFocus
        if(view!= null){
            val hideMe = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            hideMe.hideSoftInputFromWindow(view.windowToken,0)
        }
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }
}