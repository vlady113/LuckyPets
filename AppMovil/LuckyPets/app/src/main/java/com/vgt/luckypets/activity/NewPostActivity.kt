package com.vgt.luckypets.activity

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.vgt.luckypets.R
import com.vgt.luckypets.model.Post
import com.vgt.luckypets.model.Users
import com.vgt.luckypets.network.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

class NewPostActivity : AppCompatActivity() {
    private lateinit var imgNewPost: ImageView
    private lateinit var email: String
    private val PICK_IMAGE_REQUEST = 1
    private var selectedImageUri: Uri? = null
    private lateinit var checkBoxAceptarCoste: CheckBox
    private lateinit var editTextProvincia: EditText
    private lateinit var editTextFechaInicio: EditText
    private lateinit var editTextHoraInicio: EditText
    private lateinit var editTextFechaFin: EditText
    private lateinit var editTextHoraFin: EditText
    private lateinit var editTextDescripcion: EditText
    private lateinit var editTextCostePublicacion: EditText
    private lateinit var user: Users

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_new_post)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_new_post)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        email = intent.getStringExtra("email") ?: ""

        imgNewPost = findViewById(R.id.imgNewPost)
        checkBoxAceptarCoste = findViewById(R.id.checkBoxAceptarCoste)
        editTextProvincia = findViewById(R.id.EditText_Provincia)
        editTextFechaInicio = findViewById(R.id.EditText_Fecha_Inicio)
        editTextHoraInicio = findViewById(R.id.EditText_Hora_Inicio)
        editTextFechaFin = findViewById(R.id.EditText_Fecha_Fin)
        editTextHoraFin = findViewById(R.id.EditText_Hora_Fin)
        editTextDescripcion = findViewById(R.id.EditText_Descripcion)
        editTextCostePublicacion = findViewById(R.id.EditText_CostePublicacion)

        Glide.with(this)
            .load(R.drawable.placeholder_image_2)
            .into(imgNewPost)

        imgNewPost.setOnClickListener {
            openImageChooser()
        }

        fetchUserData()
    }

    private fun openImageChooser() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            selectedImageUri = data?.data
            if (selectedImageUri != null) {
                imgNewPost.setImageURI(selectedImageUri)
            }
        }
    }

    private fun fetchUserData() {
        RetrofitBuilder.api.getUsuarioByEmail(email).enqueue(object : Callback<Users> {
            override fun onResponse(call: Call<Users>, response: Response<Users>) {
                if (response.isSuccessful) {
                    user = response.body()!!
                } else {
                    Toast.makeText(this@NewPostActivity, "Error al obtener datos del usuario.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Users>, t: Throwable) {
                Toast.makeText(this@NewPostActivity, "Error de red: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getBase64FromUri(uri: Uri): String? {
        return try {
            val inputStream: InputStream? = contentResolver.openInputStream(uri)
            val byteArrayOutputStream = ByteArrayOutputStream()
            val buffer = ByteArray(1024)
            var length: Int
            while (inputStream!!.read(buffer).also { length = it } != -1) {
                byteArrayOutputStream.write(buffer, 0, length)
            }
            Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.NO_WRAP)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun showDatePickerInicio(view: View) {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                editTextFechaInicio.setText(String.format("%02d/%02d/%d", dayOfMonth, month + 1, year))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    fun showTimePickerInicio(view: View) {
        val calendar = Calendar.getInstance()
        val timePickerDialog = TimePickerDialog(
            this,
            { _, hourOfDay, minute ->
                editTextHoraInicio.setText(String.format("%02d:%02d", hourOfDay, minute))
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        )
        timePickerDialog.show()
    }

    fun showDatePickerFin(view: View) {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                editTextFechaFin.setText(String.format("%02d/%02d/%d", dayOfMonth, month + 1, year))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    fun showTimePickerFin(view: View) {
        val calendar = Calendar.getInstance()
        val timePickerDialog = TimePickerDialog(
            this,
            { _, hourOfDay, minute ->
                editTextHoraFin.setText(String.format("%02d:%02d", hourOfDay, minute))
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        )
        timePickerDialog.show()
    }

    fun publicarAnuncio(view: View?) {
        if (!checkBoxAceptarCoste.isChecked) {
            Toast.makeText(this, "Debe aceptar el coste por la publicación.", Toast.LENGTH_LONG).show()
            return
        }

        if (selectedImageUri == null || editTextProvincia.text.isEmpty() || editTextFechaInicio.text.isEmpty() || editTextHoraInicio.text.isEmpty() || editTextFechaFin.text.isEmpty() || editTextHoraFin.text.isEmpty() || editTextDescripcion.text.isEmpty() || editTextCostePublicacion.text.isEmpty()) {
            Toast.makeText(this, "Todos los campos deben estar completos, incluida una foto.", Toast.LENGTH_LONG).show()
            return
        }

        val base64Image = getBase64FromUri(selectedImageUri!!)
        if (base64Image == null) {
            Toast.makeText(this, "Error al procesar la imagen.", Toast.LENGTH_LONG).show()
            return
        }

        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
        val fechaInicio: LocalDateTime
        val fechaFin: LocalDateTime

        try {
            fechaInicio = LocalDateTime.parse("${editTextFechaInicio.text} ${editTextHoraInicio.text}", formatter)
            fechaFin = LocalDateTime.parse("${editTextFechaFin.text} ${editTextHoraFin.text}", formatter)
        } catch (e: Exception) {
            Toast.makeText(this, "Fechas no válidas. Use el formato día/mes/año hora:minuto.", Toast.LENGTH_LONG).show()
            return
        }

        if (fechaInicio.isAfter(fechaFin)) {
            Toast.makeText(this, "La fecha de inicio debe ser anterior a la fecha de fin.", Toast.LENGTH_LONG).show()
            return
        }

        val costoPublicacion = editTextCostePublicacion.text.toString().toDoubleOrNull()
        if (costoPublicacion == null || user.saldoCR < costoPublicacion) {
            Toast.makeText(this, "Saldo insuficiente para publicar el anuncio.", Toast.LENGTH_LONG).show()
            val intent = Intent(this, MyBalanceActivity::class.java)
            intent.putExtra("email", email)
            startActivity(intent)
            return
        }

        val newBalance = user.saldoCR - costoPublicacion
        user.saldoCR = newBalance

        RetrofitBuilder.api.updateUsuario(user.email, user).enqueue(object : Callback<Users> {
            override fun onResponse(call: Call<Users>, response: Response<Users>) {
                if (response.isSuccessful) {
                    val newPost = Post(
                        anuncioID = 0L,
                        usuario = user,
                        fechaInicio = fechaInicio.toString(),
                        fechaFin = fechaFin.toString(),
                        descripcion = editTextDescripcion.text.toString(),
                        estado = Post.EstadoAnuncio.PENDIENTE,
                        costoCR = costoPublicacion,
                        fotoAnuncio = base64Image
                    )
                    RetrofitBuilder.api.createPost(newPost).enqueue(object : Callback<Post> {
                        override fun onResponse(call: Call<Post>, response: Response<Post>) {
                            if (response.isSuccessful) {
                                Toast.makeText(this@NewPostActivity, "Anuncio publicado con éxito.", Toast.LENGTH_LONG).show()
                                val intent = Intent(this@NewPostActivity, PrincipalActivity::class.java)
                                intent.putExtra("email", email)
                                startActivity(intent)
                            } else {
                                Toast.makeText(this@NewPostActivity, "Error al publicar el anuncio.", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<Post>, t: Throwable) {
                            Toast.makeText(this@NewPostActivity, "Error de red: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
                        }
                    })
                } else {
                    Toast.makeText(this@NewPostActivity, "Error al actualizar saldo del usuario.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Users>, t: Throwable) {
                Toast.makeText(this@NewPostActivity, "Error de red: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun cancelarPublicacion(view: View?) {
        finish()
    }

    fun volverAtras(view: View?) {
        val intent = Intent(this, PrincipalActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
        finish()
    }
}