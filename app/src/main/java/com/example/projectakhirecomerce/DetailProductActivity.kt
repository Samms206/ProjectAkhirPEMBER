package com.example.projectakhirecomerce

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.projectakhirecomerce.model.CartEntity
import com.example.projectakhirecomerce.view.Cart.CartActivity
import com.example.projectakhirecomerce.viewmodel.CartViewModel
import com.example.projectakhirecomerce.viewmodel.CartViewModelFactory
import com.google.android.material.imageview.ShapeableImageView
import java.io.File

class DetailProductActivity : AppCompatActivity() {
    private var userId: Int = -1
    private var userEmail: String = "Sams"

    private lateinit var cartViewModel: CartViewModel

    private lateinit var decreaseButton: TextView
    private lateinit var increaseButton: TextView
    private lateinit var quantityText: TextView
    private lateinit var totalPriceText: TextView
    private lateinit var sizeClothes: LinearLayout
    private lateinit var sizeShoes: LinearLayout
    private lateinit var btnShare: ShapeableImageView

    private var quantity = 1
    private var pricePerItem = 0.0
    private val minQuantity = 1
    private val maxQuantity = 10

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
        val extras = intent.extras
        userId = extras?.getInt("id", -1) ?: -1
        userEmail = extras?.getString("email", "Sams") ?: "Sams"
        //
        val factory = CartViewModelFactory.getInstance(this) //ini
        cartViewModel = ViewModelProvider(this, factory)[CartViewModel::class.java] //ini
        //
        val productId = intent.getStringExtra("PRODUCT_ID")
        val productName = intent.getStringExtra("PRODUCT_NAME")
        val productShop = intent.getStringExtra("PRODUCT_SHOP")
        val productCategory = intent.getStringExtra("PRODUCT_CATEGORY") ?: ""
        val productPrice = intent.getStringExtra("PRODUCT_PRICE")?.toDoubleOrNull()
        val productImg = intent.getStringExtra("PRODUCT_IMG")
        val productDescription = intent.getStringExtra("PRODUCT_DESCRIPTION")
        val productRating = intent.getStringExtra("PRODUCT_RATING")

        val txtName: TextView = findViewById(R.id.txt_nameproduct_dtl)
        val txtShop: TextView = findViewById(R.id.txt_shop_dtl)
        val txtPrice: TextView = findViewById(R.id.txt_price_dtl)
        val imgProduct: ShapeableImageView = findViewById(R.id.img_product_dtl)
        val txtDescription: TextView = findViewById(R.id.txt_desc_dtl)
//        val txtRating: TextView = findViewById(R.id.txt_rating)
        val btnAddToCart: LinearLayout = findViewById(R.id.btn_addToCart)
        val btnShare:ShapeableImageView = findViewById(R.id.btn_share)

        sizeClothes = findViewById(R.id.size_clothes)
        sizeShoes = findViewById(R.id.size_shoes)

        txtName.text = productName
        txtShop.text = productShop
        txtPrice.text = "$$productPrice"
        txtDescription.text = productDescription
//        txtRating.text = productRating

        Glide.with(this)
            .load(productImg)
            .into(imgProduct)

        decreaseButton = findViewById(R.id.decrease_button)
        increaseButton = findViewById(R.id.increase_button)
        quantityText = findViewById(R.id.quantity_text)
        totalPriceText = findViewById(R.id.txt_price_dtl)

        when (productCategory) {
            "Clothes" -> {
                sizeClothes.visibility = View.VISIBLE
                sizeShoes.visibility = View.GONE
            }
            "Shoes" -> {
                sizeClothes.visibility = View.GONE
                sizeShoes.visibility = View.VISIBLE
            }
            else -> {
                sizeClothes.visibility = View.GONE
                sizeShoes.visibility = View.GONE
            }
        }

        // Set the price per item if available
        productPrice?.let {
            pricePerItem = it
        }

        updateTotalPrice()

        decreaseButton.setOnClickListener {
            if (quantity > minQuantity) {
                quantity--
                updateQuantityAndPrice()
            }
        }

        increaseButton.setOnClickListener {
            if (quantity < maxQuantity) {
                quantity++
                updateQuantityAndPrice()
            }
        }

        btnAddToCart.setOnClickListener{
            val subTotal = quantity * pricePerItem
            val newCart = CartEntity(
                id = 0,
                idUser = userId.toString(),
                idProduct = productId.toString(),
                nameProduct = productName.toString(),
                shopProduct = productShop.toString(),
                priceProduct = productPrice.toString(),
                qtyProduct = quantity.toString(),
                imgProduct = productImg.toString(),
                subTotal = subTotal.toString()
            )

            if (newCart != null) cartViewModel.insertCart(newCart)

            Toast.makeText(
                this@DetailProductActivity,
                "Data Success Added",
                Toast.LENGTH_SHORT
            ).show()

            finish()

            val intent = Intent(this, CartActivity::class.java).apply {
                putExtra("id", userId)
                putExtra("email", userEmail)
            }
            startActivity(intent)
        }

        btnShare.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Product: $productName")
                putExtra(Intent.EXTRA_TEXT, "Price: $productPrice")
                putExtra(Intent.EXTRA_TEXT, "Link Product: $productImg")
                type = "text/plain"
            }
            val whatsappInstalled = isPackageInstalled("com.whatsapp") || isPackageInstalled("com.whatsapp.w4b")
            if (whatsappInstalled) {
                sendIntent.setPackage("com.whatsapp")
                startActivity(sendIntent)
            } else {
                Toast.makeText(this, "WhatsApp tidak terinstal.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isPackageInstalled(packageName: String): Boolean {
        return try {
            packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    fun gotoMain(view: View) {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("id", userId)
            putExtra("email", userEmail)
        }
        startActivity(intent)
    }

    private fun updateQuantityAndPrice() {
        quantityText.text = quantity.toString()
        updateTotalPrice()
    }

    private fun updateTotalPrice() {
        val totalPrice = pricePerItem * quantity
        totalPriceText.text = String.format("$%.2f", totalPrice)
    }
}
