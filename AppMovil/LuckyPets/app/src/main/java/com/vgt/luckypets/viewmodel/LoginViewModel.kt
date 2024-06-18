package com.vgt.luckypets.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vgt.luckypets.model.LoginResponse
import com.vgt.luckypets.network.RetrofitBuilder
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val _user = MutableLiveData<LoginResponse?>()
    val user: LiveData<LoginResponse?> = _user

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val sharedPreferences: SharedPreferences =
        application.getSharedPreferences("login_prefs", Context.MODE_PRIVATE)

    fun loginUser(loginData: Map<String, String>) {
        viewModelScope.launch {
            RetrofitBuilder.api.loginUser(loginData).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        _user.postValue(response.body())
                    } else if (response.code() == 401) { // 401 Unauthorized
                        _errorMessage.postValue("¡Correo electrónico o contraseña incorrectos!")
                    } else {
                        _errorMessage.postValue("Error: ${response.code()} - ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    _errorMessage.postValue("Error de conexión: ${t.message}")
                }
            })
        }
    }

    fun getLoginDetails(): Pair<String?, String?> {
        val email = sharedPreferences.getString("email", null)
        val password = sharedPreferences.getString("password", null)
        return Pair(email, password)
    }

}
