package com.example.projectakhirecomerce

import android.graphics.Color
import android.os.Bundle
import android.widget.GridLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
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

        // Set click listeners
        setCategoryClickListener(categoryAll, "All")
        setCategoryClickListener(categoryClothes, "Clothes")
        setCategoryClickListener(categoryShoes, "Shoes")
        setCategoryClickListener(categoryBags, "Bags")
        setCategoryClickListener(categoryElectronics, "Electronics")

        // Set the default active category
        setActiveCategory(categoryAll, "All")

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
}