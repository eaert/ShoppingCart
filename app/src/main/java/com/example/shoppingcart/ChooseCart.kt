package com.example.shoppingcart

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.choose_cart.*
import kotlinx.android.synthetic.main.popup_add_cart.*
import kotlinx.android.synthetic.main.popup_add_cart.view.*

class ChooseCart : AppCompatActivity(), CartAdapter.OnItemClickListener {

    private val cartList = generateDummyList(10)
    private val adapter = CartAdapter(cartList, this)

    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim)}
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim)}
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim)}
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim)}

    private var clicked = false
    private var clickedRemove = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.choose_cart)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        openMenu.setOnClickListener {
            onAddButtonClicked()
        }

        removeCarts.setOnClickListener {
            Toast.makeText(this, "Remove Carts", Toast.LENGTH_SHORT).show()
            if(!clickedRemove) {
                adapter.setEnable(true)
                adapter.notifyDataSetChanged()
                clickedRemove = !clickedRemove
            }
            else{
                adapter.setEnable(false)
                adapter.notifyDataSetChanged()
                clickedRemove = !clickedRemove
            }
        }

        addCarts.setOnClickListener {
            val myViewDialog = LayoutInflater.from(this).inflate(R.layout.popup_add_cart, null)

            ArrayAdapter.createFromResource(this, R.array.spinner_array,
                android.R.layout.simple_spinner_item).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                myViewDialog.spinner.adapter = adapter
            }

            val mBuilder = AlertDialog.Builder(this).setView(myViewDialog)
            val mAlertDialog = mBuilder.show()
            myViewDialog.save.setOnClickListener {
                if(myViewDialog.spinner.selectedItem == "Create") {
                    val newCart = CartItem(
                        R.drawable.cart_image,
                        myViewDialog.cart_name.text.toString(),
                        myViewDialog.editTextTextPassword.text.toString()
                    )
                    cartList.add(cartList.size, newCart)
                    adapter.notifyItemInserted(cartList.size-1)
                }
                else{
                    //Load cart from server
                }
                mAlertDialog.dismiss()
            }
        }
    }

    fun onButtonPopupAddCart(view: View) {

    }

    fun insertCart(view: View) {
        val newCart = CartItem(R.drawable.cart_image, "New Cart", "Line 2")

        cartList.add(cartList.size, newCart)
        adapter.notifyItemInserted(cartList.size-1)
    }

//    fun enableRemoveButtons(view: View) {
//        if(removeCartAll.text.toString() == "Remove") {
//            adapter.setEnable(true)
//            adapter.notifyDataSetChanged()
//            removeCartAll.text = "Cancel"
//        }
//        else{
//            adapter.setEnable(false)
//            adapter.notifyDataSetChanged()
//            removeCartAll.text = "Remove"
//        }
//    }

    override fun onItemClick(position: Int) {
        val I = Intent(this, GroceriesList::class.java)
        startActivity(I)
    }

    private fun onAddButtonClicked(){
        setVisibility(clicked)
        setAnimation(clicked)
        clicked = !clicked
    }

    private fun setVisibility(clicked: Boolean) {
        if(!clicked){
            removeCarts.show()
            addCarts.show()
        }
        else{
            removeCarts.hide()
            addCarts.hide()
        }
    }

    private fun setAnimation(clicked: Boolean) {
        if(!clicked){
            removeCarts.startAnimation(fromBottom)
            addCarts.startAnimation(fromBottom)
            openMenu.startAnimation(rotateOpen)
        }
        else{
            removeCarts.startAnimation(toBottom)
            addCarts.startAnimation(toBottom)
            openMenu.startAnimation(rotateClose)
        }
    }

    private fun generateDummyList(size: Int): ArrayList<CartItem> {
        val list = ArrayList<CartItem>()

        for(i in 0 until size){
            val drawable = when (i % 3) {
                0 -> R.drawable.cart_image
                1 -> R.drawable.cart_note
                else -> R.drawable.cart_sun
            }

            val item = CartItem(drawable, "Item $i", "Line 2")
            list += item
        }

        return list
    }
}

