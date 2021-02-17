package com.example.shoppingcart

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.cart_items.view.*

class CartAdapter(
    private val cartList: ArrayList<CartItem>,
    private val listener: OnItemClickListener ) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>(){

    private var enable = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cart_items, parent, false)

        return CartViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val currentItem = cartList[position]

        holder.removeCartImage.isEnabled = enable
        if(!enable){
            holder.removeCartImage.visibility = View.INVISIBLE
        }
        else{
            holder.removeCartImage.visibility = View.VISIBLE
        }

        holder.imageView.setImageResource(currentItem.imageResource)
        holder.textView1.text = currentItem.text1
        holder.textView2.text = currentItem.text2

        holder.removeCartImage.setOnClickListener(holder)
    }

    override fun getItemCount() = cartList.size

    fun setEnable(boolean: Boolean){
        enable = boolean
    }

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val imageView: ImageView = itemView.cart_image
        val textView1: TextView = itemView.cart_text1
        val textView2: TextView = itemView.cart_text2
        val removeCartImage: ImageButton = itemView.removeCart

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            if(v == removeCartImage){
                if(v.isEnabled) {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        cartList.removeAt(position)
                        this@CartAdapter.notifyItemRemoved(position)
                    }
                }
            }
            else {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}