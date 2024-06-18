package es.intermodular.equipo2.incidenciasies.recyclerIncidencias

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import es.intermodular.equipo2.incidenciasies.CrearModificarIncidencia.EditIncident
import es.intermodular.equipo2.incidenciasies.Principal
import es.intermodular.equipo2.incidenciasies.R
import es.intermodular.equipo2.incidenciasies.databinding.ItemIncidenciasBinding
import es.intermodular.equipo2.incidenciasies.datos.Api
import es.intermodular.equipo2.incidenciasies.modelo.IncidenciaResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class IncidenciaViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemIncidenciasBinding.bind(view)

    fun bind(
        incidenciaResponse: IncidenciaResponse,
        onItemSelected: (IncidenciaResponse) -> Unit,
        onItemDelete: (IncidenciaResponse) -> Unit
    ) {
        binding.txtFecha.text = incidenciaResponse.fechaCreacion.toString()
        binding.txtIncidenciaID.text = "Incidencia #${incidenciaResponse.idIncidencia}"

        binding.btnEstadoIncidencai.text = incidenciaResponse.estado

        when (incidenciaResponse.estado) {
            "abierta" -> binding.btnEstadoIncidencai.setBackgroundColor(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.colorEnAbierto
                )
            )

            "asignada" -> binding.btnEstadoIncidencai.setBackgroundColor(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.colorAsignado
                )
            )

            "en_proceso" -> {
                binding.btnEstadoIncidencai.setBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.colorEnProceso
                    )
                )
                binding.btnEstadoIncidencai.text = "en proceso"
            }

            "resuelta" -> binding.btnEstadoIncidencai.setBackgroundColor(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.colorResuelto
                )
            )

            "cerrada" -> binding.btnEstadoIncidencai.setBackgroundColor(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.colorCerrado
                )
            )
        }

        if (incidenciaResponse.estado.contains("abierta") || incidenciaResponse.estado.contains("asignada")) {
            Log.i("Tipo incidencia ", incidenciaResponse.estado)
            binding.btnEditarIncidencias.setOnClickListener {
                val intent = Intent(it.context, EditIncident::class.java)
                ////Para poder pasarle la incidencia, debido a que hemos puesto la clase Incidencia como Serializable
                Log.i("Paso de incidencia ", incidenciaResponse.toString())
                intent.putExtra(EditIncident.EXTRA_EDIT_INCIDENCIA, incidenciaResponse)
                intent.putExtra("incidencia", 1)
                ContextCompat.startActivity(it.context, intent, null)
            }

            binding.btnEliminarIncidencia.setOnClickListener {
                onItemDelete(incidenciaResponse)
            }
        } else {
            binding.btnEditarIncidencias.setVisibility(View.INVISIBLE);
            binding.btnEliminarIncidencia.setVisibility(View.INVISIBLE)
        }



        binding.txtTipoIncidencia.text =
            " ${incidenciaResponse.tipoIncidencia.tipo} ${incidenciaResponse.tipoIncidencia.subtipoNombre} ${incidenciaResponse.tipoIncidencia.subSubtipo}"

        binding.root.setOnClickListener { onItemSelected(incidenciaResponse) }
    }


}


