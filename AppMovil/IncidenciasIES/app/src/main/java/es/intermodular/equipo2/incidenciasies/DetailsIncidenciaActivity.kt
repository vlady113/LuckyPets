package es.intermodular.equipo2.incidenciasies

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import es.intermodular.equipo2.incidenciasies.CrearModificarIncidencia.EditIncident
import es.intermodular.equipo2.incidenciasies.databinding.ActivityDetailsIncidenciaBinding
import es.intermodular.equipo2.incidenciasies.datos.Api
import es.intermodular.equipo2.incidenciasies.datos.ApiService
import es.intermodular.equipo2.incidenciasies.datos.RetrofitBuilder
import es.intermodular.equipo2.incidenciasies.modelo.ComentarioResponse
import es.intermodular.equipo2.incidenciasies.modelo.IncidenciaResponse
import es.intermodular.equipo2.incidenciasies.recyclerComentarios.ComentarioAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale


class DetailsIncidenciaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsIncidenciaBinding
    private lateinit var retrofit: Retrofit
    private lateinit var adapter: ComentarioAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Inflamos la vista
        binding = ActivityDetailsIncidenciaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //NOTA:
        //Cuando obtenemos el estado de la incidencia, dependiendo de su ESTADO
        //el btn (no clicable) será de un color u otro
        //Recogemos la incidenica
        val incidencia = intent.getSerializableExtra("verIncidencia") as IncidenciaResponse
        Log.i("Paso de incidencia ", incidencia.toString())

        asignarValores(incidencia)
        colorBoton(incidencia.estado)

        adapter = ComentarioAdapter()
        //Le damos los valores al RecyclerVIew
        binding.rvComentarios.layoutManager = LinearLayoutManager(this)
        binding.rvComentarios.adapter = adapter



        retrofit = RetrofitBuilder.build()
        //Recogemos los comentarios de la api
        obtenerComentariosApi(incidencia.idIncidencia)


        //Boton de enviar
        binding.imagenEnviarComentario.setOnClickListener {
            val comentario = binding.editTextNuevoComentario.text.toString()
            if (comentario.isNotEmpty()) {
                //Creamos el comentario
                Log.i("Nuevo comentario", comentario)
                crearComentario(comentario, incidencia)

            } else {
                Toast.makeText(this, "El comentario esta vacio", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnVolver.setOnClickListener {
            val intent = Intent()
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        binding.btnModificar.setOnClickListener {
            val intent = Intent(it.context, EditIncident::class.java)
            ////Para poder pasarle la incidencia, debido a que hemos puesto la clase Incidencia como Serializable
            Log.i("Paso de incidencia ", incidencia.toString())
            intent.putExtra(EditIncident.EXTRA_EDIT_INCIDENCIA, incidencia)
            intent.putExtra("incidencia", 1)
            startActivity(intent)
        }


    }


    private fun crearComentario(comentario: String, incidencia: IncidenciaResponse) {

        val fechaActual = LocalDateTime.now()
        val zonaHoraria = ZoneId.of("Europe/Madrid")
        val fechaZonificada = fechaActual.atZone(zonaHoraria)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssXXX")
        val fechaString = fechaZonificada.format(formatter)

        val nuevoComentario = ComentarioResponse(
            id = null,
            adjuntoUrl = null,
            texto = "Este es el texto del comentario",
            fechahora = fechaString,
            incidencia = incidencia,
            personal = incidencia.creador
        )

        Log.i("Nuevo comentario asignado", nuevoComentario.toString())

        Api.retrofitService.crearComentario(nuevoComentario)
            .enqueue(object : Callback<ComentarioResponse> {
                override fun onResponse(
                    call: Call<ComentarioResponse>,
                    response: Response<ComentarioResponse>
                ) {
                    if (response.isSuccessful) {
                        val comentarioCreado = response.body()
                        Log.i("Comentario creado", comentarioCreado.toString())

                    } else {
                        Log.i("Comentario NO creado", response.message())
                    }
                }

                override fun onFailure(call: Call<ComentarioResponse>, t: Throwable) {
                    Log.i("Error de conexion", t.message.toString())
                }
            })
    }


    private fun obtenerComentariosApi(idIncidencia: Int) {


        var retrofit = RetrofitBuilder.build()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val myResponse = retrofit.create(ApiService::class.java)
                    .obtenerComentariosPorIncidencia(idIncidencia)

                myResponse?.let {
                    Log.i("Comentarios incidencia $idIncidencia", it.toString())

                    if (it.isSuccessful) {
                        Log.i("Comentarios incidencia $idIncidencia", "Funciona")
                        val response: List<ComentarioResponse>? = it.body()
                        response?.let { res ->
                            runOnUiThread {
                                adapter.updateComentarios(res)
                                Log.i("Comentarios", "funciona")
                            }
                        }

                    } else {
                        Log.e("Comentarios incidencia", myResponse.toString())

                    }
                }
            } catch (e: Exception) {
                Log.e("Comentarios", "Error: ${e.message}")
            }
        }
    }

    private fun asignarValores(incidencia: IncidenciaResponse) {
        binding.txtIdIncidencia.text = "Incidencias #${incidencia.idIncidencia}"
        binding.txtTipoIncidencia.text =
            "${incidencia.tipo} ${incidencia.tipoIncidencia.subtipoNombre} ${incidencia.tipoIncidencia.subSubtipo}"
        binding.txtfechaCreacion.text = incidencia.fechaCreacion.toString()
        binding.DescripcionIncidencia.text = incidencia.descripcion

        //Como el equipo puede ser nulo, debemos controlarlo -> realizamos un if ternario
        var equipo = if (incidencia.equipo?.id.toString()
                .equals("null")
        ) "" else incidencia.equipo?.id.toString()
        binding.EquipoIncidencia.text = equipo

        binding.btnEstadoIncidencia.text = incidencia.estado

        var fechaCierre =
            if (incidencia.fechaCierre == null) "" else incidencia.fechaCierre
        binding.FechaCierreIncidencia.text = fechaCierre.toString()
    }

    private fun colorBoton(estado: String) {
        when (estado) {
            "abierta" -> binding.btnEstadoIncidencia.setBackgroundColor(
                ContextCompat.getColor(
                    this, R.color.colorEnAbierto
                )
            )

            "asginada" -> binding.btnEstadoIncidencia.setBackgroundColor(
                ContextCompat.getColor(
                    this, R.color.colorAsignado
                )
            )

            "en_proceso" -> binding.btnEstadoIncidencia.setBackgroundColor(
                ContextCompat.getColor(
                    this, R.color.colorEnProceso
                )
            )

            "cerrada" -> binding.btnEstadoIncidencia.setBackgroundColor(
                ContextCompat.getColor(
                    this, R.color.colorCerrado
                )
            )

            "resuelta" -> binding.btnEstadoIncidencia.setBackgroundColor(
                ContextCompat.getColor(
                    this, R.color.colorResuelto
                )
            )

            else -> binding.btnEstadoIncidencia.setBackgroundColor(
                ContextCompat.getColor(
                    this, R.color.theme1
                )
            )
        }
    }


}