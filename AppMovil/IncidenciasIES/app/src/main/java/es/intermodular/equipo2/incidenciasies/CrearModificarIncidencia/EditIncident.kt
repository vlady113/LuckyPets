package es.intermodular.equipo2.incidenciasies.CrearModificarIncidencia


import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import es.intermodular.equipo2.incidenciasies.databinding.ActivityEditIncidentBinding
import es.intermodular.equipo2.incidenciasies.datos.Api
import es.intermodular.equipo2.incidenciasies.modelo.CrearIncidencia
import es.intermodular.equipo2.incidenciasies.modelo.EquipoResponse
import es.intermodular.equipo2.incidenciasies.modelo.IncidenciaResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class EditIncident : AppCompatActivity() {

    companion object {
        const val EXTRA_EDIT_INCIDENCIA = "extra_incidencia_index"
        const val EXTRA_EDITED_INCIDENCIA = "extra_edited_incidencia"
        const val TIPO_INCIDENCIA: String = ""
        private const val REQUEST_CODE_EDIT = 1
    }

    private lateinit var binding: ActivityEditIncidentBinding

    //Le pasamos los parametros de el tipo de incidencia

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditIncidentBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Comprobamos desde que clase ha accedido
        //1 -> Principal (editando una incidencia)
        //0 -> SelectTypeIncidents (creando una nueva)
        var claseAnterior = intent.getIntExtra("incidencia", -1)
        if (claseAnterior == 1) {
            var incidencia =
                intent.getSerializableExtra(EXTRA_EDIT_INCIDENCIA) as IncidenciaResponse
            Log.i("Incidencia a editar", incidencia.toString())

            asignacionDatosEnCampos(incidencia)

            binding.btnAceptar.setOnClickListener {
                obtenerNuevosDatos(incidencia)
            }


        } else if (claseAnterior == 0) {

            //Lo primero de todo, obtenemos cual es el perfil del usuario
            val idPerfil = intent.getIntExtra("idPerfil", -1)
            Log.i("id del perfil", idPerfil.toString())

            //Recogemos el tipo de Incidencia y lo mostramos
            var tipoIncidencia = intent.getStringExtra("tipo").toString()
            //Le asignamos el tipo de incidencia
            binding.txtTipoIncidencia.text = tipoIncidencia

            //Le asignamos la fecha de creación, que es la actual -> No debemos asignarsela a la clase debido a que ya esta de forma automatica indicado
            val fechaActual = LocalDate.now()
            binding.txtFechaCreacion.text = fechaActual.toString()

            binding.btnAceptar.setOnClickListener {
                crearIncidencia(tipoIncidencia, idPerfil)
            }
        }

        //region FUNCIONALIDAD DE LOS BOTONES

        binding.btnCancelar.setOnClickListener {
            finish()
        }

        //endregion

    }

    private fun obtenerNuevosDatos(incidencia: IncidenciaResponse) {
        //Modificamos en la incidencia, los datos que el usuario ha podido cambiar
        incidencia.descripcion = binding.editTextDescripcion.text.toString()
        Log.i("descripcion incidencia ", incidencia.descripcion)

       //La fecha no nos hace falta

        //Obtenemos todos los posibles cambios del idEquipo
        if (incidencia.equipo == null) {
            if (binding.editTextEquipoIncidencia.text.isEmpty()) {
                //Como no se ha realizado ninguna modificacion, no lo cambiamos
                Log.i("equipoId ", "VACIOOOO")
            } else if (binding.editTextEquipoIncidencia.text.isNotEmpty()) {
                obtenerEquipo(incidencia)
            }
        } else {
            if (binding.editTextEquipoIncidencia.text.isEmpty()) {
                Log.i("equipoId ", "VACIOOOO")
            } else if (binding.editTextEquipoIncidencia.text.isNotEmpty()) {
                obtenerEquipo(incidencia)
            }
        }

        //Una vez que se han realizado las modificaciones en la actividad, modificamos la incidencia
        //Para ello, realizamos una llamada a la API 
        actualizamosIncidencia(incidencia)
    }

    private fun actualizamosIncidencia(incidencia: IncidenciaResponse) {
        val num = incidencia.idIncidencia
        Api.retrofitService.editarIncidencia(incidencia, num)
            .enqueue(object : Callback<IncidenciaResponse> {
                override fun onResponse(
                    call: Call<IncidenciaResponse>,
                    response: Response<IncidenciaResponse>
                ) {
                    if (response.isSuccessful) {
                        var incidenciaEditada = response.body()
                        Log.i("incidencia editada", incidenciaEditada.toString())
                    } else {
                        Log.i("Error al editar la incidencia ", response.message())
                    }
                }

                override fun onFailure(call: Call<IncidenciaResponse>, t: Throwable) {
                    Log.i("Error en la solicitud ", t.message.toString())
                }
            })
    }

    private fun obtenerEquipo(incidencia: IncidenciaResponse) {
        //Recogemos el id del equipo
        var equipo = binding.editTextEquipoIncidencia.text.toString()
        var equipoId = equipo.toInt()
        Log.i("equipoId ", equipoId.toString())

        //Realizamos una llamada a la API para obtener el equipo
        Api.retrofitService.obtenerEquipoPorId(equipoId).enqueue(object : Callback<EquipoResponse> {

            override fun onResponse(
                call: Call<EquipoResponse>, response: Response<EquipoResponse>
            ) {
                if (response.isSuccessful) {
                    val equipo: EquipoResponse = response.body()!!
                    Log.i("Equipo Response ", equipo.toString())

                    //Le asignamos el equipo a la incidencia
                    incidencia.equipo = equipo

                } else {
                    Log.i("Error al recoger el equipo:", response.message())
                }
            }

            override fun onFailure(call: Call<EquipoResponse>, t: Throwable) {
                Log.i("Error al realizar la solicitud:", t.message.toString())
            }
        })
    }


    private fun asignacionDatosEnCampos(incidencia: IncidenciaResponse) {
        binding.txtIncidenciaID.text = "Incidencia #${incidencia.idIncidencia}"
        binding.txtTipoIncidencia.text =
            "${incidencia.tipo} ${incidencia.tipoIncidencia.subtipoNombre} ${incidencia.tipoIncidencia.subSubtipo}"
        binding.txtFechaCreacion.text = incidencia.fechaCreacion.toString()
        binding.editTextDescripcion.setText(incidencia.descripcion)

        //Como el equipo puede ser nulo, debemos controlarlo -> realizamos un if ternario
        var equipoo = if (incidencia.equipo?.id.toString()
                .equals("null")
        ) "" else incidencia.equipo?.id.toString()
        binding.editTextEquipoIncidencia.setText(equipoo)

        binding.btnEstadoIncidencia.text = incidencia.estado
    }


    private fun crearIncidencia(
        tipoIncidencia: String, idPerfil: Int
    ) {
        //Para crear la incidencia, la llamada la API recibe una incidencia
        //por lo tanto, 1º debemos crearla
        var nuevaIncidencia = CrearIncidencia()

        //Obtenemos la fecha actual
        var fechaActual = LocalDateTime.now()
        //Parseamos la fecha para que tenga el mismo formato que en Worbench
        val formatoDeseado = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        nuevaIncidencia.fechaCreacion = fechaActual.format(formatoDeseado)
        Log.i("Fecha creacion", nuevaIncidencia.fechaCreacion)

        //Le pasamos el id del creador, que corresponde con el del perfil
        nuevaIncidencia.creador_id = idPerfil
        Log.i("Nueva incidencia -> creador_id", nuevaIncidencia.creador_id.toString())

        //---------- PASO DE TIPO Y SUBTIPO ------------
        //Una vez obtenido el tipo de incidencia, en un string
        //Debemos separarlos, para poder hacer una llamada a la API
        //para que nos devuelva el id
        val tipoI = tipoIncidencia.split(" ")
        val tipo = tipoI[0]
        val subTipo = tipoI[1]
        val subSubTipo =
            tipoI.getOrElse(2) { "" } // if there are only 2 words, word3 will be an empty string

        Log.i("tipo:", tipo)
        Log.i("subTipo:", subTipo)
        Log.i("subSubTipo", subSubTipo)

        //El tipo de datos, se lo podemos pasar de forma automatica
        nuevaIncidencia.tipo = tipo


        //Llamamos a la api, para obtener el id del subtipo
        Api.retrofitService.obtenerIdPorTipoSubtipoYSubsubtipo(tipo, subTipo, subSubTipo)
            .enqueue(object : Callback<Int> {
                override fun onResponse(call: Call<Int>, response: Response<Int>) {
                    if (response.isSuccessful) {
                        val id: Int? = response.body()
                        nuevaIncidencia.subtipo_id = response.body()!!
                        Toast.makeText(this@EditIncident, "ID $id", Toast.LENGTH_SHORT).show()
                        // Usa el id como necesites
                        Log.i("Id del subtipo", id.toString())


                    } else {
                        Log.i("Error al crear la incidencia:", response.message())
                    }
                }

                override fun onFailure(call: Call<Int>, t: Throwable) {
                    Log.i("Error al realizar la solicitud:", t.message.toString())
                }
            })


        //Le asiganamos la descripcion
        nuevaIncidencia.descripcion = binding.editTextDescripcion.text.toString()
        Log.i("Descripcion incidencia ", nuevaIncidencia.descripcion)


        //Le asignamos el equipoId
        if (binding.editTextEquipoIncidencia.text.isNotEmpty()) {
            //Convertimos el texto en un int
            var equipoIncidenca = binding.editTextEquipoIncidencia.text.toString()
            var idEquipo = equipoIncidenca.toInt()
            nuevaIncidencia.equipoId = idEquipo
        }

        Log.i("nueva incidencia ", nuevaIncidencia.toString())

        // region ------------------------------ NO FUNCIONA -------------------------
        //Como ya hemos pasado todos los parametros, llamamos a la API y creamos la incidencia
        Api.retrofitService.crearIncidencia(nuevaIncidencia)
            .enqueue(object : Callback<IncidenciaResponse> {
                override fun onResponse(
                    call: Call<IncidenciaResponse>,
                    response: Response<IncidenciaResponse>
                ) {
                    Log.i("Datos incidencia nueva", nuevaIncidencia.toString())
                    if (response.isSuccessful) {
                        val myresponse = response.body()
                        Log.i("Creamos incidencia", myresponse.toString())

                        //No esta recogiendo la fecha de creacion
                        // Haz lo que necesites con la respuesta de la API
                    } else {
                        Log.i("Error al crear la incidencia:", response.message())
                    }
                }

                override fun onFailure(call: Call<IncidenciaResponse>, t: Throwable) {
                    Log.i("Error al realizar la solicitud:", t.message.toString())
                }
            })
        //endregion

    }


}