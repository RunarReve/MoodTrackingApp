package com.rever.moodtrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Set up all text
        tvLoginHeader.text = "Log In"
        tvGotAccount.text = "Do not have an account:"
        tvGoToLogin.text = "Register"
        btnLogIn.text = "Log in"

        tvGoToLogin.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        btnLogIn.setOnClickListener {
            if (etLoginEmail.text.trim().isEmpty()){
                Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if(etLoginPassword.text.trim().isEmpty()) {
                Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val email = etLoginEmail.text.toString().trim()
            val password = etLoginPassword.text.toString().trim()

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, MainActivity::class.java)

                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    intent.putExtra("user_id",
                        FirebaseAuth.getInstance().currentUser!!.uid)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, task.exception!!.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}