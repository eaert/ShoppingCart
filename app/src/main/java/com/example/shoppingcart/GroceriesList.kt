package com.example.shoppingcart

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import kotlinx.android.synthetic.main.groceries_items.view.*
import kotlinx.android.synthetic.main.groceries_list.*
import kotlinx.android.synthetic.main.popup_add_groceries.view.*
import kotlinx.android.synthetic.main.popup_item_image.view.*


class GroceriesList : AppCompatActivity(), GroceriesAdapter.OnItemClickListener {

    private val groceriesList = ArrayList<GroceriesItem>()
    private val adapter = GroceriesAdapter(groceriesList, this)

    private val RESULT_LOAD_IMAGE = 100
    val REQUEST_CODE = 100

    private var imageData: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.groceries_list)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView2)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        newGroceries.setOnClickListener {
            val myViewDialog = LayoutInflater.from(this).inflate(R.layout.popup_add_groceries, null)
            val mBuilder = AlertDialog.Builder(this).setView(myViewDialog)
            val mAlertDialog = mBuilder.show()

            myViewDialog.upload_item.setOnClickListener {
                val i = Intent(
                    Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
                startActivityForResult(i, RESULT_LOAD_IMAGE)
            }

            myViewDialog.save2.setOnClickListener {
                if(imageData != null) {
                    val item = GroceriesItem(
                        imageData,
                        myViewDialog.groceries_name.text.toString(),
                        myViewDialog.groceries_num.text.toString()
                    )
                    groceriesList.add(groceriesList.size, item)
                    adapter.notifyItemInserted(groceriesList.size - 1)
                }
                else{
                    val item = GroceriesItem(
                        Uri.parse("android.resource://com.example.shoppingcart/" + R.drawable.ic_broken_image),
                        myViewDialog.groceries_name.text.toString(),
                        myViewDialog.groceries_num.text.toString()
                    )
                    groceriesList.add(groceriesList.size, item)
                    adapter.notifyItemInserted(groceriesList.size - 1)
                }

                imageData = null

                mAlertDialog.dismiss()
            }
        }

    }


    override fun onItemClick(position: Int) {
        val myViewDialog = LayoutInflater.from(this).inflate(R.layout.popup_item_image, null)
        val mBuilder = AlertDialog.Builder(this).setView(myViewDialog)
        val mAlertDialog = mBuilder.show()

        myViewDialog.item_image.setImageURI(groceriesList[position].imageResource)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
            imageData = data?.data // handle chosen image
        }
    }
}

