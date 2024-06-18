package es.intermodular.equipo2.incidenciasies

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import es.intermodular.equipo2.incidenciasies.databinding.ActivitySpecificListIncidentsBinding
import es.intermodular.equipo2.incidenciasies.datos.Api
import es.intermodular.equipo2.incidenciasies.modelo.IncidenciaResponse
import es.intermodular.equipo2.incidenciasies.recyclerIncidencias.IncidenciaAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SpecificListIncidents : AppCompatActivity() {

    private lateinit var binding: ActivitySpecificListIncidentsBinding
    private lateinit var adapter: IncidenciaAdapter
    lateinit var incidencias: Array<IncidenciaResponse>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflar la vista
        binding = ActivitySpecificListIncidentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recogemos el valor del Intent que se ha pasado
        val typeOfIncidents = intent.extras?.getString("EXTRA_TYPE_INCIDENTS").orEmpty()

        binding.tituloIncidencias.text = "Incidencias $typeOfIncidents"

        binding.menuAtras.setOnClickListener {
            finish()
        }

        // Configurar el RecyclerView
        // Inicializa el adaptador con una lista vacía
        adapter = IncidenciaAdapter(emptyList(), ::navigateToDetalil, ::eliminarIncidencia)

        binding.rvIncidenciasEspecificas.adapter = IncidenciaAdapter(
            onItemSelect = { incidence -> navigateToDetalil(incidence) },
            onItemDelete = { incidence -> eliminarIncidencia(incidence) }
        )
        binding.rvIncidenciasEspecificas.layoutManager = LinearLayoutManager(this)
        binding.rvIncidenciasEspecificas.adapter = adapter

        // Obtener la lista de incidencias del Intent
        incidencias =
            intent.getSerializableExtra("EXTRA_INCIDENCIAS") as Array<IncidenciaResponse>


        // Actualizar el adaptador con la lista de incidencias
        adapter.updateIncidencias(incidencias.toList())

        when (typeOfIncidents) {
            "Abiertas" -> {
                binding.toolbar.setBackgroundColor(getResources().getColor(R.color.colorEnAbierto))
            }

            "Asignadas" -> {
                binding.toolbar.setBackgroundColor(getResources().getColor(R.color.colorAsignado))
            }

            "En proceso" -> {
                binding.toolbar.setBackgroundColor(getResources().getColor(R.color.colorEnProceso))
            }

            "Resueltas" -> {
                binding.toolbar.setBackgroundColor(getResources().getColor(R.color.colorResuelto))
            }

            "Cerradas" -> {
                binding.toolbar.setBackgroundColor(getResources().getColor(R.color.colorCerrado))
            }

        }
    }

    private fun navigateToDetalil(incidencia: IncidenciaResponse) {
        val intent = Intent(this, DetailsIncidenciaActivity::class.java)
        intent.putExtra("verIncidencia", incidencia)
        startActivity(intent)
    }

    private fun eliminarIncidencia(incidenciasResponse: IncidenciaResponse) {

        // Creamos la ventana emergente
        val dialog = MaterialAlertDialogBuilder(this)
            .setTitle("Eliminar")
            .setMessage("¿Estas seguro de que desea eliminar la incidencia?")

            // Acción que se realiza al dar "Añadir"
            .setPositiveButton("SI") { _, _ ->

                Log.i("Incidencia para elliminar", incidenciasResponse.toString())
                Api.retrofitService.borrarIncidencia(incidenciasResponse.idIncidencia)
                    .enqueue(object : Callback<IncidenciaResponse> {
                        override fun onResponse(
                            call: Call<IncidenciaResponse>,
                            response: Response<IncidenciaResponse>
                        ) {
                            if (response.isSuccessful) {
                                val myResponse = response.body()
                                Log.i("Incidencia eliminada ", myResponse.toString())

                                val intent = Intent(applicationContext, Principal::class.java)
                                intent.putExtra("ID_PERFIL_EXTRA", incidencias[0].creador.id)
                                startActivity(intent)

                            } else {
                                Log.i("No se ha podido eliminar", response.message())
                            }
                        }

                        override fun onFailure(call: Call<IncidenciaResponse>, t: Throwable) {
                            Log.i("Eror en la solicitud", t.message.toString())
                        }
                    })
            }
            .setNegativeButton("NO", null)
            .create()
        dialog.show()
    }
}