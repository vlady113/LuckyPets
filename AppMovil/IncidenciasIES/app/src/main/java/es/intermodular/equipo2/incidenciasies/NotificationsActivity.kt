package es.intermodular.equipo2.incidenciasies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import es.intermodular.equipo2.incidenciasies.databinding.ActivityNotificationsBinding

class NotificationsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //inflamos la vista
        binding = ActivityNotificationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //----------------- DAMOS FUNCIONALIDAD AL TOOL BAR ------------
        binding.menuAtras.setOnClickListener {
            finish()
        }
    }
}