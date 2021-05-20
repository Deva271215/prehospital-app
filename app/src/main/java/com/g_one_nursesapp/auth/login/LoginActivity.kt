package com.g_one_nursesapp.auth.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.g_one_nursesapp.ProfileActivity
import com.g_one_nursesapp.R
import com.g_one_nursesapp.api.RetrofitClient
import com.g_one_nursesapp.auth.data.storage.SharedPrefManager
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        buttonLogin.setOnClickListener {

            val email = loginEmail.text.toString().trim()
            val password = loginPassword.text.toString().trim()

            if(email.isEmpty()){
                editTextEmail.error = "Email required"
                editTextEmail.requestFocus()
                return@setOnClickListener
            }


            if(password.isEmpty()){
                editTextPassword.error = "Password required"
                editTextPassword.requestFocus()
                return@setOnClickListener
            }

            RetrofitClient.instance.userLogin(email, password)
                .enqueue(object: Callback<LoginResponse> {
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        var res = response

                        Log.d("response check ", "" + response.body()?.message.toString())
                        if (res.code() == 200) {
                            SharedPrefManager.getInstance(applicationContext).saveUser(response.body()?.data!!)
                            loginResultEmitter.emit(LoginResult.Success)
                        } else {
                            try {
                                val jObjError =
                                    JSONObject(response.errorBody()!!.string())
                                loginResultEmitter.emit(LoginResult.NetworkError(jObjError.getString("user_msg")))
                            } catch (e: Exception) {
                                // showToast(applicationContext,e.message) // TODO
                                Log.e("errorrr", e.message)
                            }
                        }

                    }
                })
        }
    }

    override fun onStart() {
        super.onStart()

        if(SharedPrefManager.getInstance(this).isLoggedIn){
            val intent = Intent(applicationContext, ProfileActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
        }
    }
}

