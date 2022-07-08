package com.example.intercomapp

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class DeleteActivity : AppCompatActivity() {

    private lateinit var backDelete: Button
    private lateinit var deleteAccount: Button
    private lateinit var nameDelete: EditText
    private lateinit var pinDelete: EditText
    private lateinit var addressDelete: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete)
        backDelete = findViewById(R.id.back_delete)
        deleteAccount = findViewById(R.id.delete_account)
        nameDelete = findViewById(R.id.name_delete)
        pinDelete = findViewById(R.id.pin_delete)
        addressDelete = findViewById(R.id.address_delete)

        //setting up database variables
        var helper = DatabaseHelper(applicationContext)
        var db = helper.readableDatabase

        //when delete button is clicked delete account
        deleteAccount.setOnClickListener {
            var alertDialog = AlertDialog.Builder(applicationContext)
                .setTitle("Warning")
                .setMessage("Are you sure you want to delete: ${nameDelete.text.toString()} ?")
                .setPositiveButton("Yes", DialogInterface.OnClickListener{ dialog, which ->
                    if(helper.deleteAccount(nameDelete.text.toString(),addressDelete.text.toString(),pinDelete.text.toString())){
                        Toast.makeText(applicationContext,"USER: ${nameDelete.text.toString()} Deleted",
                            Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(applicationContext,"Error Deleting", Toast.LENGTH_LONG).show()
                    }
                }).setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->  })
                .setIcon(R.drawable.ic_baseline_warning_24)
            alertDialog.show()
        }

        //when back button(delete) is clicked show home screen and hide rest
        backDelete.setOnClickListener {
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}