package com.example.projectakhirecomerce.view.Cart

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projectakhirecomerce.DetailProductActivity
import com.example.projectakhirecomerce.R
import com.example.projectakhirecomerce.model.CartEntity
import com.example.projectakhirecomerce.model.Product
import com.google.android.material.imageview.ShapeableImageView

class CartAdapter(private var cartList: List<CartEntity>) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    interface OnItemClickCallback {
        fun onItemClicked(data: CartEntity)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setCartList(cartList: List<CartEntity>) {
        this.cartList = cartList
        notifyDataSetChanged()
    }

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productName: TextView = itemView.findViewById(R.id.txt_nameProduct_cart)
        val productShop: TextView = itemView.findViewById(R.id.txt_shop_cart)
        val productPrice: TextView = itemView.findViewById(R.id.txt_price_cart)
        val productQty: TextView = itemView.findViewById(R.id.txt_qty_cart)
        val productImg: ShapeableImageView = itemView.findViewById(R.id.img_cart)
        val btnDelete: RelativeLayout = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cart = cartList[position]
        holder.productName.text = cart.nameProduct
        holder.productShop.text = cart.shopProduct
        holder.productPrice.text = "$" + cart.priceProduct
        holder.productQty.text = "Qty : " + cart.qtyProduct
        Glide.with(holder.productImg.context)
            .load(cart.imgProduct)
            .into(holder.productImg)

        //delete
        holder.btnDelete.setOnClickListener {
            onItemClickCallback?.onItemClicked(cart)
        }
    }

    override fun getItemCount(): Int {
        return cartList.size
    }
}