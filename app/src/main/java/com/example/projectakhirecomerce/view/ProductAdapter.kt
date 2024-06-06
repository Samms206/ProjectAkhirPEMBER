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

//        holder.itemView.setOnClickListener {
//            val intent = Intent(holder.itemView.context, DetailProductActivity::class.java).apply {
//                putExtra("PRODUCT_ID", product.id)
//                putExtra("PRODUCT_NAME", product.name)
//                putExtra("PRODUCT_SHOP", product.shop)
//                putExtra("PRODUCT_CATEGORY", product.category)
//                putExtra("PRODUCT_PRICE", product.price)
//                putExtra("PRODUCT_IMG", product.img)
//                putExtra("PRODUCT_DESCRIPTION", product.description)
//                putExtra("PRODUCT_RATING", product.rating)
//            }
//            holder.itemView.context.startActivity(intent)
//        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}

