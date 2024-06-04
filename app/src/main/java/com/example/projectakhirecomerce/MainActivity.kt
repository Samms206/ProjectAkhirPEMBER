package com.example.projectakhirecomerce

import android.graphics.Color
import android.os.Bundle
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectakhirecomerce.api.ApiResponse
import com.example.projectakhirecomerce.repository.ProductRepository
import com.example.projectakhirecomerce.view.ProductAdapter
import com.example.projectakhirecomerce.viewmodel.ProductViewModel
import com.example.projectakhirecomerce.viewmodel.ProductViewModelFactory

class MainActivity : AppCompatActivity() {

    private var currentActiveCategory: RelativeLayout? = null

    private lateinit var productViewModel: ProductViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var productAdapter: ProductAdapter
    //
    private lateinit var homeIcon: ImageView
    private lateinit var cartIcon: ImageView
    private lateinit var notificationIcon: ImageView
    private lateinit var profileIcon: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Show Product
        recyclerView = findViewById(R.id.rv_product)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        productAdapter = ProductAdapter(emptyList())
        recyclerView.adapter = productAdapter

        val repository = ProductRepository()
        val factory = ProductViewModelFactory(repository)
        productViewModel = ViewModelProvider(this, factory).get(ProductViewModel::class.java)

        productViewModel.products.observe(this) { response ->
            when (response) {
                is ApiResponse.Success -> {
                    productAdapter.setProductList(response.data)
                }
                is ApiResponse.Error -> {
                    val errorMessage = response.message
                }
                is ApiResponse.Loading -> {
                    // Show loading indicator
                }
            }
        }

        // Initialize category views
        val categoryAll: RelativeLayout = findViewById(R.id.category_all)
        val categoryClothes: RelativeLayout = findViewById(R.id.category_clothes)
        val categoryShoes: RelativeLayout = findViewById(R.id.category_shoes)
        val categoryBags: RelativeLayout = findViewById(R.id.category_bags)
        val categoryElectronics: RelativeLayout = findViewById(R.id.category_electronics)
        //menu
        homeIcon = findViewById(R.id.home_icon)
        cartIcon = findViewById(R.id.cart_icon)
        notificationIcon = findViewById(R.id.notification_icon)
        profileIcon = findViewById(R.id.profile_icon)

        homeIcon.setOnClickListener { setActiveIcon(homeIcon) }
        cartIcon.setOnClickListener { setActiveIcon(cartIcon) }
        notificationIcon.setOnClickListener { setActiveIcon(notificationIcon) }
        profileIcon.setOnClickListener { setActiveIcon(profileIcon) }

        // Set click listeners
        setCategoryClickListener(categoryAll, "All")
        setCategoryClickListener(categoryClothes, "Clothes")
        setCategoryClickListener(categoryShoes, "Shoes")
        setCategoryClickListener(categoryBags, "Bags")
        setCategoryClickListener(categoryElectronics, "Electronics")

        // Set the default active category
        setActiveCategory(categoryAll, "All")
        homeIcon.setImageResource(R.drawable.homeactive)

        // Load initial data
        productViewModel.getProducts()
    }

    private fun setCategoryClickListener(category: RelativeLayout, categoryName: String) {
        category.setOnClickListener {
            setActiveCategory(category, categoryName)
        }
    }

    private fun setActiveCategory(category: RelativeLayout, categoryName: String) {
        currentActiveCategory?.let {
            // Reset previous active category
            it.setBackgroundResource(R.drawable.border)
            val previousText = it.getChildAt(0) as TextView
            previousText.setTextColor(Color.BLACK)
        }

        // Set new active category
        category.setBackgroundResource(R.drawable.border_active)
        val currentText = category.getChildAt(0) as TextView
        currentText.setTextColor(Color.WHITE)
        currentActiveCategory = category

        // Fetch products based on the selected category
        if (categoryName == "All") {
            productViewModel.getProducts()
        } else {
            productViewModel.getProductsByCategory(categoryName)
        }
    }

    private fun setActiveIcon(activeIcon: ImageView) {
        // Reset all icons to default
        homeIcon.setImageResource(R.drawable.home)
        cartIcon.setImageResource(R.drawable.cartmenu)
        notificationIcon.setImageResource(R.drawable.notification)
        profileIcon.setImageResource(R.drawable.profile)

        // Set the clicked icon to its active state
        when (activeIcon) {
            homeIcon -> activeIcon.setImageResource(R.drawable.homeactive)
            cartIcon -> activeIcon.setImageResource(R.drawable.cardmenuactive)
            notificationIcon -> activeIcon.setImageResource(R.drawable.notificationactive)
            profileIcon -> activeIcon.setImageResource(R.drawable.profileactive)
        }
    }
}