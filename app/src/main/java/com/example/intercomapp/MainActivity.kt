package com.example.intercomapp

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.intercomapp.ui.theme.IntercomAppTheme
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.delete_account.*
import kotlinx.android.synthetic.main.pin_login.*
import kotlinx.android.synthetic.main.user_registration.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //setting up database variables
        var helper = DatabaseHelper(applicationContext)
        var db = helper.readableDatabase

        //show home page by default
        showHome()

        //when register button is clicked show register screen and hide rest
        register_home.setOnClickListener{
            showRegistration()
        }

        //when back button(register) is clicked show home screen and hide rest
        back.setOnClickListener {
            showHome()
        }

        //when back button(login) is clicked show home screen and hide rest
        back_login.setOnClickListener {
            showHome()
        }

        //when back button(delete) is clicked show home screen and hide rest
        back_delete.setOnClickListener {
            showHome()
        }

        //when login button is clicked show log in screen and hide rest
        login.setOnClickListener {
            showLogIn()
        }

        //when delete button is clicked show delete screen and hide rest
        delete_main.setOnClickListener {
            showDelete()
        }

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
            Toast.makeText(this,"ACCOUNT CREATED",Toast.LENGTH_LONG).show()
        }

        //when sign in button is clicked check if data entered matches any record in database
        sign_in.setOnClickListener {
            var args = listOf<String>(name_login.text.toString(),pin_login.text.toString()).toTypedArray()
            var rs = db.rawQuery("SELECT * FROM USERS WHERE name = ? AND pin = ?",args)
            hideMyKeyboard()
            if(rs.moveToNext()){
                Toast.makeText(applicationContext,"Welcome!",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(applicationContext,"Wrong Password or Username",Toast.LENGTH_LONG).show()
            }
        }

        //when delete button is clicked delete account
        delete_account.setOnClickListener {
            var alertDialog = AlertDialog.Builder(applicationContext)
                .setTitle("Warning")
                .setMessage("Are you sure you want to delete: ${name_delete.text.toString()} ?")
                .setPositiveButton("Yes", DialogInterface.OnClickListener{dialog, which ->
                    if(helper.deleteAccount(name_delete.text.toString(),address_delete.text.toString(),pin_delete.text.toString())){
                        Toast.makeText(applicationContext,"USER: ${name_delete.text.toString()} Deleted",Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(applicationContext,"Error Deleting",Toast.LENGTH_LONG).show()
                    }
                }).setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->  })
                .setIcon(R.drawable.ic_baseline_warning_24)
            alertDialog.show()
        }
    }

    //function to show register screen
    private fun showRegistration(){
        register_layout.visibility = View.VISIBLE
        login_layout.visibility = View.GONE
        delete_layout.visibility = View.GONE
        home.visibility = View.GONE
    }
    //function to show pin pad
    private fun showLogIn(){
        register_layout.visibility = View.GONE
        login_layout.visibility = View.VISIBLE
        delete_layout.visibility = View.GONE
        home.visibility = View.GONE
    }

    //function to show delete page
    private fun showDelete(){
        register_layout.visibility = View.GONE
        login_layout.visibility = View.GONE
        delete_layout.visibility = View.VISIBLE
        home.visibility = View.GONE
    }

    //function to show home
    private fun showHome(){
        register_layout.visibility = View.GONE
        login_layout.visibility = View.GONE
        delete_layout.visibility = View.GONE
        home.visibility = View.VISIBLE
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

