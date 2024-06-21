package com.example.projectakhirecomerce.view.Profile

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.projectakhirecomerce.MainActivity
import com.example.projectakhirecomerce.R
import com.example.projectakhirecomerce.model.UserEntity
import com.example.projectakhirecomerce.utils.DependencyInjection
import com.example.projectakhirecomerce.view.Auth.LoginActivity
import com.example.projectakhirecomerce.viewmodel.UserViewModel
import com.example.projectakhirecomerce.viewmodel.UserViewModelFactory

class ProfileActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel

    private var userId: Int = -1
    private var userEmail: String = "Sams"
    private var userPass: String = "123"

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val emailEditText = findViewById<EditText>(R.id.tf_username_pr)
        val passwordEditText = findViewById<EditText>(R.id.tf_password_pr)

        val extras = intent.extras
        userId = extras?.getInt("id", -1) ?: -1
        userEmail = extras?.getString("email", "Sams") ?: "Sams"
        userPass = extras?.getString("password", "123") ?: "123"

        emailEditText.setText(userEmail)
        passwordEditText.setText(userPass)

        val repository = DependencyInjection.provideUserRepository(this)
        userViewModel = ViewModelProvider(this, UserViewModelFactory(repository))
            .get(UserViewModel::class.java)

        val btnUpdate:Button = findViewById(R.id.btn_update)
        btnUpdate.setOnClickListener{
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    this@ProfileActivity,
                    "All fields are required.",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            //
            val updatedUser = UserEntity(id = userId, email = email, password = password)
            userViewModel.updateUser(updatedUser)
            Toast.makeText(
                this@ProfileActivity,
                "Data Success Updated, Please Logout and Login again!",
                Toast.LENGTH_SHORT
            ).show()
            //
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

        val btnLogout : LinearLayout = findViewById(R.id.btn_logout)
        btnLogout.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Logout Confirmation")
            builder.setMessage("Are you sure you want to log out?")
            builder.setPositiveButton("Yes") { _, _ ->
                // If user presses "Yes"
                val intent = Intent(applicationContext, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
            builder.setNegativeButton("No") { dialog, _ ->
                // If user presses "No", dismiss the dialog
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
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