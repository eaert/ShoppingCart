package com.example.shoppingcart

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.*

class CreateNewCart : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_cart)

        val create = findViewById<Button>(R.id.save)
        val cart_name = findViewById<EditText>(R.id.cart_name)
        val password = findViewById<TextView>(R.id.password)

        //Create the spinner list and load it
        val spinner = findViewById<Spinner>(R.id.spinner)

        ArrayAdapter.createFromResource(this, R.array.spinner_array,
            android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
            spinner.adapter = adapter
        }

        create.setOnClickListener{
            if(spinner.selectedItem.toString() == "Load"){
                //access to the server and find Cart with the right name, password has to match
            }
            else{
                //create new Cart by adding the Card name and the password to the server,
                //** must check the Cart name doesn't exist already
            }
            val I = Intent(this, ChooseCart::class.java)
            startActivity(I)
        }
    }
}