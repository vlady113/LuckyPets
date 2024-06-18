package com.vgt.luckypets.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.vgt.luckypets.R
import com.vgt.luckypets.adapter.PostAdapter
import com.vgt.luckypets.databinding.ActivityPrincipalBinding
import com.vgt.luckypets.model.Post
import com.vgt.luckypets.model.Users
import com.vgt.luckypets.network.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PrincipalActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityPrincipalBinding
    private lateinit var email: String
    private lateinit var balanceEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.principal)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            WindowInsetsCompat.CONSUMED
        }

        email = intent.getStringExtra("email") ?: getEmailFromPreferences()
        if (email.isEmpty()) {
            Toast.makeText(this, "Error: No se proporcionó correo electrónico.", Toast.LENGTH_LONG)
                .show()
            finish()
            return
        }

        Log.d("PrincipalActivity", "Correo recibido: $email")

        balanceEditText = findViewById(R.id.EditText_MyBalance_Now)
        balanceEditText.setOnClickListener {
            showMyBalanceActivity()
        }
        fetchUserBalance()

        val menuAjustes: ImageView = findViewById(R.id.menuAjustes)
        menuAjustes.setOnClickListener { view ->
            showPopupMenu(view)
        }
    }

    private fun getEmailFromPreferences(): String {
        val sharedPreferences = getSharedPreferences("login_prefs", MODE_PRIVATE)
        return sharedPreferences.getString("email", "") ?: ""
    }

    private fun fetchUserBalance() {
        RetrofitBuilder.api.getUsuarioByEmail(email).enqueue(object : Callback<Users> {
            override fun onResponse(call: Call<Users>, response: Response<Users>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    val balance = user?.saldoCR ?: 0.0
                    balanceEditText.setText(String.format("%.2f CR", balance))
                } else {
                    Toast.makeText(
                        this@PrincipalActivity,
                        "Error al obtener el saldo.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<Users>, t: Throwable) {
                Toast.makeText(
                    this@PrincipalActivity,
                    "Error de red: ${t.localizedMessage}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun showPopupMenu(view: View) {
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.popup_menu, null)

        val popupWindow = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        )

        // Configurar el contenido del popup
        popupView.findViewById<TextView>(R.id.action_my_data).setOnClickListener {
            showMyDataActivity()
            popupWindow.dismiss()
        }
        popupView.findViewById<TextView>(R.id.action_change_email).setOnClickListener {
            showChangeEmailActivity()
            popupWindow.dismiss()
        }
        popupView.findViewById<TextView>(R.id.action_change_password).setOnClickListener {
            showChangePasswordActivity()
            popupWindow.dismiss()
        }
        popupView.findViewById<TextView>(R.id.action_about).setOnClickListener {
            showAboutActivity()
            popupWindow.dismiss()
        }
        popupView.findViewById<TextView>(R.id.action_help).setOnClickListener {
            popupWindow.dismiss()
        }
        popupView.findViewById<TextView>(R.id.logOut).setOnClickListener {
            Toast.makeText(this, "Se ha cerrado sesión", Toast.LENGTH_SHORT).show()
            popupWindow.dismiss()
            logOut()
        }

        // Mostrar el popup
        popupWindow.showAsDropDown(view, -100, 0)
    }

    private fun showMyBalanceActivity() {
        val intent = Intent(this, MyBalanceActivity::class.java)
        intent.putExtra("email", email)
        startActivity(intent)
    }

    private fun showMyDataActivity() {
        val intent = Intent(this, MyDataActivity::class.java)
        intent.putExtra("email", email)
        startActivity(intent)
    }

    private fun showChangeEmailActivity() {
        val intent = Intent(this, ChangeEmailActivity::class.java)
        intent.putExtra("email", email)
        startActivity(intent)
    }

    private fun showChangePasswordActivity() {
        val intent = Intent(this, ChangePasswordActivity::class.java)
        intent.putExtra("email", email)
        startActivity(intent)
    }

    private fun showAboutActivity() {
        val intent = Intent(this, AboutActivity::class.java)
        startActivity(intent)
    }

    private fun logOut() {
        clearLoginPreferences()
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
        finish()
    }

    private fun clearLoginPreferences() {
        val sharedPreferences = getSharedPreferences("login_prefs", MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("email", "")
            putString("password", "")
            putBoolean("remember_me", false)
            apply()
        }
    }

    fun volverAtras(view: View?) {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
        finish() // Opcional, solo en caso de querer eliminar esta actividad del stack
    }
}
