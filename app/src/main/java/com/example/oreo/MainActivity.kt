package com.example.oreo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var button: Button
    private lateinit var customButton: Button
    private lateinit var textView: TextView
    private var user: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()
        Log.d("MainActivity", "onCreate called")

        auth = Firebase.auth
        button = findViewById(R.id.logout)
        customButton = findViewById(R.id.custom_button)
        textView = findViewById(R.id.user_details)

        user = auth.currentUser
        Log.d("MainActivity", "Current user: $user")

        if (user == null) {
            Log.d("MainActivity", "No user logged in, redirecting to Login.")
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish()
        } else {
            Log.d("MainActivity", "User logged in: ${user?.email}")
            val userUid = getUserUidFromSharedPreferences()
            Log.d("MainActivity", "Retrieved UID from SharedPreferences: $userUid")
            textView.text = userUid ?: "No UID found"
        }

        button.setOnClickListener {
            Log.d("MainActivity", "Logout button clicked")
            auth.signOut()
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish()
        }

        customButton.setOnClickListener {
            Log.d("MainActivity", "Custom button clicked")
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getUserUidFromSharedPreferences(): String? {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("UID", "No UID found")
    }
}
