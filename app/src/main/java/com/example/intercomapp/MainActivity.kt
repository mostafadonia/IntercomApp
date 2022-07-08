package com.example.intercomapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity


class MainActivity : ComponentActivity() {
    private lateinit var registerHome: Button
    private lateinit var loginButton: Button
    private lateinit var deleteMain: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        registerHome = findViewById(R.id.register_home)
        loginButton = findViewById(R.id.login)
        deleteMain = findViewById(R.id.delete_main)

        //setting up database variables
        var helper = DatabaseHelper(applicationContext)
        var db = helper.readableDatabase


        //when register button is clicked show register screen and hide rest
        registerHome.setOnClickListener{
            intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }

        //when login button is clicked show log in screen and hide rest
        loginButton.setOnClickListener {
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        //when delete button is clicked show delete screen and hide rest
        deleteMain.setOnClickListener {
            intent = Intent(this, DeleteActivity::class.java)
            startActivity(intent)
        }

    }

}

