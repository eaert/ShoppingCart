package com.example.shoppingcart

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

//need to extract the dictionary from the server
var Users = mutableMapOf("Eran" to "6512")

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)

        val login = findViewById<Button>(R.id.login)
        val signup = findViewById<Button>(R.id.signup)

        login.setOnClickListener {
            if (Users[username.text.toString()] == password.text.toString()) {
                val I = Intent(this, ChooseCart::class.java)
                startActivity(I)
                //Need to get the user details from server - do no forget need to load the user currently carts
            }
            else{
                null
                password.error = "Username or Password incorrect"
                //Put an error message - Username or Password aren't correct
            }
        }

        signup.setOnClickListener {
            if(Users[username.text.toString()] == null){
                Users[username.text.toString()] = password.text.toString()
                //Need to create a file in the server for the new user
            }
            else{
                null
                username.error = "Username already exist"
                //Put an error message - Username already exist
            }
        }
    }
}