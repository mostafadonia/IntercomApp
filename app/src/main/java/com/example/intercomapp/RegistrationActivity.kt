package com.example.intercomapp

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class RegistrationActivity : AppCompatActivity() {

    private lateinit var save: Button
    lateinit var name: EditText
    lateinit var address: EditText
    lateinit var pin: EditText
    private lateinit var back: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        save = findViewById(R.id.save)
        name = findViewById(R.id.name)
        address = findViewById(R.id.address)
        pin = findViewById(R.id.pin)
        back = findViewById(R.id.back)

        //setting up database variables
        var helper = DatabaseHelper(applicationContext)
        var db = helper.readableDatabase

        //when register button is clicked input data entered into database
        save.setOnClickListener {
            var cv = ContentValues()
            cv.put("name", name.text.toString())
            cv.put("address",address.text.toString())
            cv.put("pin",pin.text.toString())
            db.insert("USERS",null,cv)
            hideMyKeyboard()
            name.setText("")
            address.setText("")
            pin.setText("")
            Toast.makeText(this,"ACCOUNT CREATED", Toast.LENGTH_LONG).show()
            openCamera()
        }

        //when back button(register) is clicked show home screen and hide rest
        back.setOnClickListener {
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

    private fun openCamera(){
        val intent = Intent(this, CameraActivity::class.java)
        startActivity(intent)
    }
}