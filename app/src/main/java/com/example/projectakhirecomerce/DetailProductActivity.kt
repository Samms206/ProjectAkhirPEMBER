package com.example.projectakhirecomerce

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView

class DetailProductActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_product)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val productName = intent.getStringExtra("PRODUCT_NAME")
        val productShop = intent.getStringExtra("PRODUCT_SHOP")
        val productPrice = intent.getStringExtra("PRODUCT_PRICE")
        val productImg = intent.getStringExtra("PRODUCT_IMG")
        val productDescription = intent.getStringExtra("PRODUCT_DESCRIPTION")
        val productRating = intent.getStringExtra("PRODUCT_RATING")

        val txtName: TextView = findViewById(R.id.txt_nameproduct_dtl)
        val txtShop: TextView = findViewById(R.id.txt_shop_dtl)
        val txtPrice: TextView = findViewById(R.id.txt_price_dtl)
        val imgProduct: ShapeableImageView = findViewById(R.id.img_product_dtl)
        val txtDescription: TextView = findViewById(R.id.txt_desc_dtl)
//        val txtRating: TextView = findViewById(R.id.txt_rating)

        txtName.text = productName
        txtShop.text = productShop
        txtPrice.text = "$$productPrice"
        txtDescription.text = productDescription
//        txtRating.text = productRating

        Glide.with(this)
            .load(productImg)
            .into(imgProduct)
    }

    fun gotoMain(view: View) {
        Intent(this, MainActivity::class.java).also {
            startActivity(it)
        }
    }
}