package es.intermodular.equipo2.incidenciasies.menuPrincipal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import es.intermodular.equipo2.incidenciasies.R

class AyudaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        // Encontrar el botón de retroceso en el layout inflado
        val menuAtras = findViewById<ImageView>(R.id.menuAtras)

        // Configurar el OnClickListener para el botón de retroceso
        menuAtras.setOnClickListener {
            // Cerrar la actividad actual y volver a la actividad anterior
            finish()
        }
    }
}