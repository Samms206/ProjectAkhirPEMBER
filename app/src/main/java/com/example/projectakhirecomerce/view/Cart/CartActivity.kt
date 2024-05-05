package com.example.projectakhirecomerce.view.Cart

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectakhirecomerce.MainActivity
import com.example.projectakhirecomerce.R
import com.example.projectakhirecomerce.model.CartEntity
import com.example.projectakhirecomerce.view.ProductAdapter
import com.example.projectakhirecomerce.viewmodel.CartViewModel
import com.example.projectakhirecomerce.viewmodel.CartViewModelFactory
import com.example.projectakhirecomerce.viewmodel.ProductViewModel

class CartActivity : AppCompatActivity() {

    private var userId: Int = -1
    private var userEmail: String = "Sams"
    private var userPass: String = "123"

    private lateinit var cartViewModel: CartViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var cartAdapter: CartAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cart)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //INTENT DATA
        val extras = intent.extras
        userId = extras?.getInt("id", -1) ?: -1
        userEmail = extras?.getString("email", "Sams") ?: "Sams"
        userPass = extras?.getString("password", "123") ?: "123"

        val factory = CartViewModelFactory.getInstance(this)
        cartViewModel = ViewModelProvider(this, factory)[CartViewModel::class.java]
        recyclerView = findViewById(R.id.rv_cart)
        recyclerView.layoutManager = LinearLayoutManager(this)

        cartViewModel.getCartByUserId(userId.toString()).observe(this) { cartData ->
            if (cartData != null) {
                cartAdapter = CartAdapter(cartData) //ini
                recyclerView.adapter = cartAdapter

                cartAdapter.setOnItemClickCallback(object : CartAdapter.OnItemClickCallback{
                    override fun onItemClicked(data: CartEntity) {
                        cartViewModel.deleteCart(data)
                    }
                })
            }
        }
    }

    fun gotoMain(view: View) {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("id", userId)
            putExtra("email", userEmail)
            putExtra("password", userPass)
        }
        startActivity(intent)
    }
}