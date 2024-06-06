package com.example.projectakhirecomerce.view.Auth

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
import com.example.projectakhirecomerce.MainActivity
import com.example.projectakhirecomerce.R
import com.example.projectakhirecomerce.utils.DependencyInjection
import com.example.projectakhirecomerce.viewmodel.UserViewModel
import com.example.projectakhirecomerce.viewmodel.UserViewModelFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        showDataUser()

        val repository = DependencyInjection.provideUserRepository(this)
        userViewModel = ViewModelProvider(this, UserViewModelFactory(repository))
            .get(UserViewModel::class.java)

        val emailEditText = findViewById<EditText>(R.id.email)
        val passwordEditText = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.btn_login)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            userViewModel.login(email, password) { user ->
                if (user != null) {
                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                    Intent(this, MainActivity::class.java).also {
                        it.putExtra("id", user.id)
                        it.putExtra("email", user.email)
                        startActivity(it)
                    }
                } else {
                    Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    fun gotoRegister(view: View) {
        Intent(this, RegisterActivity::class.java).also {
            startActivity(it)
        }
    }

    fun showDataUser(){
        //Menampilkan data di tabel user
        val repository = DependencyInjection.provideUserRepository(this)
        userViewModel = ViewModelProvider(this, UserViewModelFactory(repository))[UserViewModel::class.java]
        userViewModel.getAllUser().observe(this) { userList ->
            for (user in userList) {
                Log.d(
                    "DatabaseData",
                    "ID: ${user.id}, Email: ${user.email}, Password: ${user.password}"
                )
            }
        }
    }
}