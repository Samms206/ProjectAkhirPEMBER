package com.example.projectakhirecomerce.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projectakhirecomerce.R
import com.example.projectakhirecomerce.model.Product
import com.google.android.material.imageview.ShapeableImageView

class ProductAdapter(private var productList: List<Product>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    fun setProductList(productList: List<Product>) {
        this.productList = productList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.productName.text = product.name
        holder.productShop.text = product.shop
        holder.productPrice.text = product.price
        Glide.with(holder.productImg.context)
            .load(product.img)
            .into(holder.productImg)

        val layoutParams = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
        if ((position + 1) % 2 == 0) {
            layoutParams.topMargin = 200 // Tambahkan margin top 20dp
        } else {
            layoutParams.topMargin = 0 // Reset margin top untuk kolom kiri
        }
        holder.itemView.layoutParams = layoutParams
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productName: TextView = itemView.findViewById(R.id.txt_nameproduct)
        val productShop: TextView = itemView.findViewById(R.id.txt_shop)
        val productPrice: TextView = itemView.findViewById(R.id.txt_price)
        val productImg: ShapeableImageView = itemView.findViewById(R.id.img_product)
    }
}

