package com.rever.moodtrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Set up all text
        tvLoginHeader.text = "Register Account"
        tvGotAccount.text = "Already got an account: "
        tvGoToLogin.text = "Log In"
        btnLogIn.text = "Register"

        tvGoToLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))

        }

        btnLogIn.setOnClickListener {
            if (etLoginEmail.text.trim().isEmpty()){
                Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if(etLoginPassword.text.trim().length < 6) {
                Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val email = etLoginEmail.text.toString().trim()
            val password = etLoginPassword.text.toString().trim()

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener{ task ->
                if (task.isComplete) {
                    val firebaseUser: FirebaseUser = task.result!!.user!!
                    Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, UserInfoEditActivity::class.java)

                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    intent.putExtra("user_id", firebaseUser.uid)
                    intent.putExtra("from", "register")
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, task.exception!!.message.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}