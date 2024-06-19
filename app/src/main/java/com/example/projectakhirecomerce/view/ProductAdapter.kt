package com.example.projectakhirecomerce.view

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projectakhirecomerce.DetailProductActivity
import com.example.projectakhirecomerce.R
import com.example.projectakhirecomerce.api.ApiResponse
import com.example.projectakhirecomerce.model.Product
import com.google.android.material.imageview.ShapeableImageView

class ProductAdapter(private var productList: List<Product>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private var itemClickListener: OnItemClickListener? = null
    private var stateFav = false

    interface OnItemClickListener {
        fun onItemClicked(product: Product)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    fun setProductList(productList: List<Product>) {
        this.productList = productList
        notifyDataSetChanged()
    }

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productName: TextView = itemView.findViewById(R.id.txt_nameproduct)
        val productShop: TextView = itemView.findViewById(R.id.txt_shop)
        val productPrice: TextView = itemView.findViewById(R.id.txt_price)
        val productImg: ShapeableImageView = itemView.findViewById(R.id.img_product)
        val iconLove: ShapeableImageView = itemView.findViewById(R.id.icon_love)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.productName.text = product.name
        holder.productShop.text = product.shop
        holder.productPrice.text = "$" + product.price
        Glide.with(holder.productImg.context)
            .load(product.img)
            .into(holder.productImg)

        holder.itemView.setOnClickListener {
            itemClickListener?.onItemClicked(product)
        }

        holder.iconLove.setOnClickListener {
            stateFav = !stateFav
            holder.iconLove.setImageResource(if (stateFav) R.drawable.loveactive else R.drawable.love)
        }

    }

    override fun getItemCount(): Int {
        return productList.size
    }
}

