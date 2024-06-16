package com.example.oreo


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
    private lateinit var textView: TextView
    private var user: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("MainActivity", "onCreate called")

        auth = Firebase.auth
        button = findViewById(R.id.logout)
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
            textView.text = user?.email ?: "Unknown"
        }

        button.setOnClickListener {
            Log.d("MainActivity", "Logout button clicked")
            auth.signOut()
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish()
        }
    }
}
