package com.vgt.luckypets.network

import com.vgt.luckypets.model.LoginResponse
import com.vgt.luckypets.model.Post
import com.vgt.luckypets.model.TarjetaBancaria
import com.vgt.luckypets.model.Users
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    // --- Métodos relacionados con usuarios ---

    // Método para iniciar sesión
    @POST("usuarios/login")
    fun loginUser(@Body loginData: Map<String, String>): Call<LoginResponse>

    // Método para registrar un nuevo usuario
    @POST("usuarios")
    fun registerUser(@Body newUser: Users): Call<Users>

    // Método para enviar un código de restablecimiento de contraseña
    @POST("usuarios/sendResetCode")
    fun sendResetCode(@Body emailData: Map<String, String>): Call<Void>

    // Método para verificar el código de restablecimiento de contraseña
    @POST("usuarios/verifyResetCode")
    fun verifyResetCode(@Body requestData: Map<String, String>): Call<Boolean>

    // Método para cambiar la contraseña
    @POST("usuarios/changePassword")
    fun changePassword(@Body requestData: Map<String, String>): Call<Void>

    // Método para obtener un usuario por ID
    @GET("usuarios/{id}")
    fun getUserById(@Path("id") userId: Long): Call<Users>

    // Método para obtener un usuario por correo electrónico
    @GET("usuarios/email/{email}")
    fun getUsuarioByEmail(@Path("email") email: String): Call<Users>

    // Método para actualizar un usuario
    @PUT("usuarios/email/{email}")
    fun updateUsuario(@Path("email") email: String, @Body usuario: Users): Call<Users>

    // --- Métodos relacionados con tarjetas bancarias ---

    // Método para registrar una nueva tarjeta bancaria
    @POST("tarjetas")
    fun createTarjeta(@Body tarjeta: TarjetaBancaria): Call<TarjetaBancaria>

    // Método para obtener imágenes de tarjetas bancarias
    @GET("images/card-logos")
    fun getCardLogos(): Call<Map<String, String>>

    // Método para obtener tarjetas por correo electrónico
    @GET("usuarios/tarjetas/email/{email}")
    fun getTarjetasByEmail(@Path("email") email: String): Call<List<TarjetaBancaria>>

    // Método para verificar si una tarjeta ya está registrada por número de tarjeta
    @GET("tarjetas/{numeroTarjeta}")
    fun getTarjetaByNumero(@Path("numeroTarjeta") numeroTarjeta: Long): Call<TarjetaBancaria>

    // Método para obtener tarjetas por ID de usuario
    @GET("tarjetas/usuario/{userID}")
    fun getTarjetasByUserId(@Path("userID") userID: Long): Call<List<TarjetaBancaria>>

    // Método para eliminar una tarjeta por ID
    @DELETE("tarjetas/{id}")
    fun deleteTarjeta(@Path("id") id: Long): Call<Void>

    // --- Métodos relacionados con anuncios ---

    // Método para obtener un anuncio por ID
    @GET("anuncios/{id}")
    fun getPostById(@Path("id") id: Long): Call<Post>

    // Método para obtener todos los anuncios
    @GET("anuncios")
    fun getPosts(): Call<List<Post>>
}
