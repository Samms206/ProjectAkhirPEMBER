package com.example.projectakhirecomerce.view.Auth

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.projectakhirecomerce.R
import com.example.projectakhirecomerce.model.UserDatabase
import com.example.projectakhirecomerce.utils.DependencyInjection
import com.example.projectakhirecomerce.viewmodel.UserViewModel
import com.example.projectakhirecomerce.viewmodel.UserViewModelFactory

class RegisterActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val repository = DependencyInjection.provideRepository(this)
        userViewModel = ViewModelProvider(this, UserViewModelFactory(repository))
            .get(UserViewModel::class.java)

        val emailEditText = findViewById<EditText>(R.id.email_reg)
        val passwordEditText = findViewById<EditText>(R.id.password_reg)
        val registerButton = findViewById<Button>(R.id.btn_registers)

        registerButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            val newUser = UserDatabase(email = email, password = password)
            userViewModel.insertUser(newUser)
            Toast.makeText(
                this@RegisterActivity,
                "Data Success Added",
                Toast.LENGTH_SHORT
            ).show()

            userViewModel.getAllUser().observe(this) { userList ->
                for (user in userList) {
                    Log.d(
                        "DatabaseData",
                        "ID: ${user.id}, Email: ${user.email}, Password: ${user.password}"
                    )
                }
            }
            finish()
        }
    }

    fun gotoLogin(view: View) {
        Intent(this, LoginActivity::class.java).also {
            startActivity(it)
        }
    }
}