package com.example.pastagram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.parse.ParseUser
import org.w3c.dom.Text

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (ParseUser.getCurrentUser()!=null){
            goToMainActivity()
        }

        findViewById<Button>(R.id.btnLogin).setOnClickListener {
            val username = findViewById<EditText>(R.id.etName).text.toString()
            val password = findViewById<EditText>(R.id.etPassword).text.toString()
            loginUser(username,password)
        }

        findViewById<Button>(R.id.btnSignUp).setOnClickListener {
            val username = findViewById<EditText>(R.id.etName).text.toString()
            val password = findViewById<EditText>(R.id.etPassword).text.toString()
            singUpUser(username,password)
        }
    }

    private fun singUpUser(username: String, password: String){
        val user = ParseUser()

        // Set fields for the user to be created
        user.setUsername(username)
        user.setPassword(password)

        user.signUpInBackground { e ->
            if (e == null) {
                // Hooray! Let them use the app now.
            } else {
                e.printStackTrace()
                // Sign up didn't succeed. Look at the ParseException
                // to figure out what went wrong
            }
        }

    }

    private fun loginUser(username: String, password: String){
        ParseUser.logInInBackground(username, password, ({ user, e ->
            if (user != null) {
                Log.i(TAG, "Successfully logged in")
                goToMainActivity()
            } else {
                e.printStackTrace()
                Toast.makeText(this, "Error logging in", Toast.LENGTH_SHORT).show()
            }})
        )

    }

    private fun goToMainActivity(){
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
    companion object{
        val TAG = "LoginActivity"
    }
}