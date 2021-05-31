package com.g_one_nursesapp.auth.signup


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.g_one_nursesapp.R
import com.g_one_nursesapp.api.RetrofitClient
import com.g_one_nursesapp.api.response.SignUpResponse
import com.g_one_nursesapp.auth.login.LoginActivity
import com.g_one_nursesapp.entity.UserEntity
import kotlinx.android.synthetic.main.activity_signup.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Daftar"

        submitButton()
        buttonToLogin()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun buttonToLogin(){
        btnToSignIn.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun submitButton(){
        signUpButton.setOnClickListener{
            val groupName = editTextGroupName.text.toString().trim()
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()
            val noHp = editTextNoHp.text.toString().trim()


            if(groupName.isEmpty()) {
                editTextGroupName.error = "Masukan nama group anda"
                editTextEmail.requestFocus()
                return@setOnClickListener
            }

            if(email.isEmpty()){
                editTextEmail.error = "Masukan email anda"
                editTextEmail.requestFocus()
                return@setOnClickListener
            }

            if(password.isEmpty()){
                editTextPassword.error = "Setting password anda"
                editTextPassword.requestFocus()
                return@setOnClickListener
            }

            if(noHp.isEmpty()){
                editTextNoHp.error = "Masukan nomor hp anda"
                editTextNoHp.requestFocus()
                return@setOnClickListener
            }

            val user = UserEntity(email = email, groupName = groupName, password = password, noHp = noHp)
            RetrofitClient.instance.createUser(user)
                .enqueue(object: Callback<SignUpResponse>{

                    override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                        Toast.makeText(this@SignupActivity, "Error", Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {
                        Log.i("resposne", response.body().toString())

                      if (response.isSuccessful){
                            val intent = Intent(this@SignupActivity, LoginActivity::class.java)
                            startActivity(intent)
                          Toast.makeText(applicationContext, response.body()?.message, Toast.LENGTH_LONG).show()
                      } else {
                          Toast.makeText(applicationContext, "Opps!", Toast.LENGTH_LONG).show()
                      }

                    }

                })
        }
    }

}