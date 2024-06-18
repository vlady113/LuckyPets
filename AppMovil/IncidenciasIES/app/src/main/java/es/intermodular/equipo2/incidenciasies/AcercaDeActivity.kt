package es.intermodular.equipo2.incidenciasies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class AcercaDeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        // Encontrar el botón de retroceso en el layout inflado
        val menuAtras = findViewById<ImageView>(R.id.menuAtras)

        // Configurar el OnClickListener para el botón de retroceso
        menuAtras.setOnClickListener {
            // Cerrar la actividad actual y volver a la actividad anterior
            finish()
        }
    }
}