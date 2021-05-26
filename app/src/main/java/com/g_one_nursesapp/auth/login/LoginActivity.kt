package com.g_one_nursesapp.auth.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.g_one_nursesapp.MainActivity
import com.g_one_nursesapp.ProfileActivity
import com.g_one_nursesapp.R
import com.g_one_nursesapp.api.RetrofitClient
import com.g_one_nursesapp.api.response.LoginData
import com.g_one_nursesapp.api.response.LoginResponse
import com.g_one_nursesapp.auth.signup.SignupActivity
import com.g_one_nursesapp.databinding.ActivityLoginBinding
import com.g_one_nursesapp.entity.UserEntity
import com.g_one_nursesapp.preference.UserPreference
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var preference: UserPreference
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preference = UserPreference(applicationContext)

        buttonLogin.setOnClickListener {
            val email = loginEmail.text.toString().trim()
            val password = loginPassword.text.toString().trim()

            if(email.isEmpty()){
                editTextEmail.error = "Email wajib diisi"
                editTextEmail.requestFocus()
                return@setOnClickListener
            }
            if(password.isEmpty()){
                editTextPassword.error = "Password wajib diisi"
                editTextPassword.requestFocus()
                return@setOnClickListener
            }

            val loginUser = UserEntity(email = email, password = password)
            RetrofitClient.instance.userLogin(loginUser)
                    .enqueue(object: Callback<LoginResponse> {
                        override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                            if (response.isSuccessful) {
                                val loginData = LoginData(response.body()?.data?.user, response.body()?.data?.access_token)
                                preference.setLoginData(loginData)

                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(applicationContext, "Email atau password salah.", Toast.LENGTH_LONG).show()
                            }
                        }

                        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                            Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                        }

                    })
        }

        onSignInButtonClicked()
    }

    private fun onSignInButtonClicked() {
        binding.btnToSignIn.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }
}

