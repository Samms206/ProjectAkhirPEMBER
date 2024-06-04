package com.example.projectakhirecomerce

import android.graphics.Color
import android.os.Bundle
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private var currentActiveCategory: RelativeLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Initialize category views
        val categoryAll: RelativeLayout = findViewById(R.id.category_all)
        val categoryClothes: RelativeLayout = findViewById(R.id.category_clothes)
        val categoryShoes: RelativeLayout = findViewById(R.id.category_shoes)
        val categoryBags: RelativeLayout = findViewById(R.id.category_bags)
        val categoryElectronics: RelativeLayout = findViewById(R.id.category_electronics)

        // Set click listeners
        setCategoryClickListener(categoryAll)
        setCategoryClickListener(categoryClothes)
        setCategoryClickListener(categoryShoes)
        setCategoryClickListener(categoryBags)
        setCategoryClickListener(categoryElectronics)

        // Set the default active category
        setActiveCategory(categoryAll)
    }

    private fun setCategoryClickListener(category: RelativeLayout) {
        category.setOnClickListener {
            setActiveCategory(category)
        }
    }

    private fun setActiveCategory(category: RelativeLayout) {
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
    }
}