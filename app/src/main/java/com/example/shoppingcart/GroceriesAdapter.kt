package com.example.shoppingcart

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.groceries_items.view.*

class GroceriesAdapter (
    private val groceriesList : ArrayList<GroceriesItem>,
    private val listener: GroceriesAdapter.OnItemClickListener) :
    RecyclerView.Adapter<GroceriesAdapter.GroceriesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceriesAdapter.GroceriesViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.groceries_items, parent, false)

        return GroceriesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GroceriesAdapter.GroceriesViewHolder, position: Int) {
        val currentItem = groceriesList[position]

        holder.imageView.setImageURI(currentItem.imageResource)
        holder.textView1.text = currentItem.text1
        holder.textView2.text = currentItem.text2

        holder.numPlusImage.setOnClickListener(holder)
        holder.numMinusImage.setOnClickListener(holder)
    }

    override fun getItemCount() = groceriesList.size

    inner class GroceriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val imageView: ImageView = itemView.groceries_image
        val textView1: TextView = itemView.groceries_text1
        val textView2: TextView = itemView.groceries_text2
        val numPlusImage: ImageButton = itemView.NumPlus
        val numMinusImage: ImageButton = itemView.NumMinus


        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            var numOfGroceries = textView2.text.toString().toInt();
            if(v == numPlusImage){
                numOfGroceries++
                textView2.text = numOfGroceries.toString()
            }
            else if(v == numMinusImage){
                numOfGroceries--
                textView2.text = numOfGroceries.toString()
                if(numOfGroceries == 0){
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        groceriesList.removeAt(position)
                        this@GroceriesAdapter.notifyItemRemoved(position)
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