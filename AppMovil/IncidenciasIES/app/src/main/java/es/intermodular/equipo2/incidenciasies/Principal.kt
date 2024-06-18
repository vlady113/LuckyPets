package es.intermodular.equipo2.incidenciasies


import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import es.intermodular.equipo2.incidenciasies.CrearModificarIncidencia.SelectTypeIncidents
import es.intermodular.equipo2.incidenciasies.databinding.ActivityPrincipalBinding
import es.intermodular.equipo2.incidenciasies.datos.Api
import es.intermodular.equipo2.incidenciasies.datos.ApiService
import es.intermodular.equipo2.incidenciasies.datos.RetrofitBuilder
import es.intermodular.equipo2.incidenciasies.menuPrincipal.AcercaDeActivity
import es.intermodular.equipo2.incidenciasies.menuPrincipal.AyudaActivity
import es.intermodular.equipo2.incidenciasies.modelo.IncidenciaResponse
import es.intermodular.equipo2.incidenciasies.recyclerIncidencias.IncidenciaAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class Principal : AppCompatActivity() {

    private lateinit var binding: ActivityPrincipalBinding
    private lateinit var retrofit: Retrofit
    private lateinit var adapter: IncidenciaAdapter

    private var idPerfil: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Inflamos la vista
        binding = ActivityPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userPreferences = UserPreferences(this)
        if (!userPreferences.isLoggedIn) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            return
        }
        isOnline(this)
        //recuperamos el id del usuario que se ha pasado anteriormente, mediante un Intent
        idPerfil = userPreferences.userId
        adapter = IncidenciaAdapter(emptyList(), ::navigateToDetalil, ::eliminarIncidencia)
        retrofit = RetrofitBuilder.build()
        initUI()


        //recuperamos el id del usuario que se ha pasado anteriormente, mediante un Intent
        // idPerfil = intent.getIntExtra("ID_PERFIL_EXTRA", -1)

        //region FUNCINALIDAD BOTONES
        //Boton de añadir
        binding.btnAddIncidencias.setOnClickListener {
            val intent = Intent(this, SelectTypeIncidents::class.java)
            intent.putExtra("idPerfil", idPerfil)
            startActivity(intent)
        }

        //Botones del ToolBar --> Apartado de notificaciones
        binding.menuNotificaciones.setOnClickListener {
            val intent = Intent(this, NotificationsActivity::class.java)
            startActivity(intent)
        }
        //endregion

        //region Damos funcionalidad al menu
        val menuAjustes = findViewById<ImageView>(R.id.menuAjustes)

        // Configurar el OnClickListener para el ícono de ajustes
        menuAjustes.setOnClickListener { view ->
            // Crear un objeto PopupMenu asociado con el ícono de ajustes
            val popupMenu = PopupMenu(this@Principal, view)

            // Inflar el menú desde el archivo XML
            popupMenu.menuInflater.inflate(R.menu.menu_settings, popupMenu.menu)

            // Configurar el Listener para manejar las acciones del menú
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_help -> {
                        // Lógica para el elemento "Ayuda"
                        mostrarLayoutAyuda()
                        true
                    }

                    R.id.action_about -> {
                        // Lógica para el elemento "Acerca de"
                        mostrarLayoutAcercaDe()
                        true
                    }

                    R.id.logOut -> {
                        cerrarSesion()
                        true

                    }

                    else -> false
                }
            }

            // Mostrar el menú desplegable
            popupMenu.show()
        }

        //endregion


    }

    private fun cerrarSesion() {
        val userPreferences = UserPreferences(this)
        userPreferences.isLoggedIn = false
        //Limpiamos el id del usuario
        userPreferences.userId = -1

        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun initUI() {
        binding.rvIncidencias.layoutManager = LinearLayoutManager(this)
        //Damos valor al recycler view
        retrofit = RetrofitBuilder.build()

        binding.rvIncidencias.adapter = IncidenciaAdapter(
            onItemSelect = { incidence -> navigateToDetalil(incidence) },
            onItemDelete = { incidence -> eliminarIncidencia(incidence) }
        )


        binding.rvIncidencias.adapter = adapter

        //Mostramos los items
        obtenerIncidencias(idPerfil)
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

                                obtenerIncidencias(idPerfil)
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

    private fun navigateToDetalil(incidencia: IncidenciaResponse) {
        val intent = Intent(this, DetailsIncidenciaActivity::class.java)
        intent.putExtra("verIncidencia", incidencia)
        startActivity(intent)
    }

    private fun obtenerIncidencias(idUsuarioPrueba: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val myResponse = retrofit.create(ApiService::class.java)
                    .getIncidenciasUsuario(idUsuarioPrueba)

                myResponse?.let {
                    Log.i("Incidecias usuario", it.toString())

                    if (it.isSuccessful) {
                        Log.i("Incidecias usuario", "Funciona")
                        val response: List<IncidenciaResponse>? = it.body()
                        response?.let { res ->
                            runOnUiThread {
                                adapter.updateIncidencias(res)

                                // Actualizar el texto de los botones
                                funcionalidadBotones(res)
                            }
                        }
                    } else {
                        Log.e("Incidencias error", "Error al obtener las incidencias")
                    }
                }
            } catch (e: Exception) {
                Log.e("Incidencias", "Error: ${e.message}")
            }
        }
    }

    private fun funcionalidadBotones(response: List<IncidenciaResponse>) {
        // Establecer el texto del botón "btnIncidenciasTotales" con el número total de incidencias
        binding.btnIncidenciasTotales.text = "${response.size} Incidencias"

        // Agrupar las incidencias por estado
        val estados = response.groupBy { it.estado }

        // Obtener la lista de incidencias en cada estado o una lista vacía si no hay incidencias en ese estado
        val incidenciasAbiertasFuncionalidad = estados["abierta"] ?: emptyList()
        val incidenciasAsignadasFuncionalidad = estados["asignada"] ?: emptyList()
        val incidenciasEnProcesoFuncionalidad = estados["en_proceso"] ?: emptyList()
        val incidenciasResueltasFuncionalidad = estados["resuelta"] ?: emptyList()
        val incidenciasCerradasFuncionalidad = estados["cerrada"] ?: emptyList()

        // Establecer el texto de los botones con la cantidad de incidencias en cada estado
        binding.btnAbiertas.text = "${incidenciasAbiertasFuncionalidad.size} Abiertas"
        binding.btnAsignadas.text = "${incidenciasAsignadasFuncionalidad.size} Asignadas"
        binding.btnEnProceso.text = "${incidenciasEnProcesoFuncionalidad.size} En Proceso"
        binding.btnResueltas.text = "${incidenciasResueltasFuncionalidad.size} Resueltas"
        binding.btnCerradas.text = "${incidenciasCerradasFuncionalidad.size} Cerradas"

        // Configurar el OnClickListener para cada botón

        binding.btnAbiertas.setOnClickListener {
            if (incidenciasAbiertasFuncionalidad.size != 0) {
                val intent = Intent(this, SpecificListIncidents::class.java)
                intent.putExtra("EXTRA_TYPE_INCIDENTS", "Abiertas")
                intent.putExtra(
                    "EXTRA_INCIDENCIAS",
                    incidenciasAbiertasFuncionalidad.toTypedArray()
                )
                startActivity(intent)
            } else {
                Toast.makeText(this, "No hay incidencias", Toast.LENGTH_SHORT).show()
            }
        }


        binding.btnAsignadas.setOnClickListener {
            if (incidenciasAsignadasFuncionalidad.size != 0) {
                val intent = Intent(this, SpecificListIncidents::class.java)
                intent.putExtra("EXTRA_TYPE_INCIDENTS", "Asignadas")
                intent.putExtra(
                    "EXTRA_INCIDENCIAS",
                    incidenciasAsignadasFuncionalidad.toTypedArray()
                )
                startActivity(intent)
            } else {
                Toast.makeText(this, "No hay incidencias", Toast.LENGTH_SHORT).show()
            }
        }



        binding.btnEnProceso.setOnClickListener {
            if (incidenciasEnProcesoFuncionalidad.size != 0) {
                val intent = Intent(this, SpecificListIncidents::class.java)
                intent.putExtra("EXTRA_TYPE_INCIDENTS", "En proceso")
                intent.putExtra(
                    "EXTRA_INCIDENCIAS",
                    incidenciasEnProcesoFuncionalidad.toTypedArray()
                )
                startActivity(intent)
            } else {
                Toast.makeText(this, "No hay incidencias", Toast.LENGTH_SHORT).show()
            }
        }



        binding.btnResueltas.setOnClickListener {
            if (incidenciasResueltasFuncionalidad.size != 0) {
                val intent = Intent(this, SpecificListIncidents::class.java)
                intent.putExtra("EXTRA_TYPE_INCIDENTS", "Resueltas")
                intent.putExtra(
                    "EXTRA_INCIDENCIAS",
                    incidenciasResueltasFuncionalidad.toTypedArray()
                )
                startActivity(intent)
            } else {
                Toast.makeText(this, "No hay incidencias", Toast.LENGTH_SHORT).show()
            }
        }


        binding.btnCerradas.setOnClickListener {
            if (incidenciasCerradasFuncionalidad.size != 0) {
                val intent = Intent(this, SpecificListIncidents::class.java)
                intent.putExtra("EXTRA_TYPE_INCIDENTS", "Cerradas")
                intent.putExtra(
                    "EXTRA_INCIDENCIAS",
                    incidenciasCerradasFuncionalidad.toTypedArray()
                )
                startActivity(intent)
            } else {
                Toast.makeText(this, "No hay incidencias", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun mostrarLayoutAyuda() {
        // Crear un intent para iniciar la actividad AyudaActivity
        val intent = Intent(this, AyudaActivity::class.java)
        // Iniciar la actividad AyudaActivity
        startActivity(intent)
    }

    private fun mostrarLayoutAcercaDe() {
        // Crear un intent para iniciar la actividad AcercaDeActivity
        val intent = Intent(this, AcercaDeActivity::class.java)
        // Iniciar la actividad AcercaDeActivity
        startActivity(intent)
    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isAvailable && networkInfo.isConnected
    }

}