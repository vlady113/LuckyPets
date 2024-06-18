package es.intermodular.equipo2.incidenciasies

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import es.intermodular.equipo2.incidenciasies.databinding.SplashScreenBinding

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Inflamos la vista
        val binding = SplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Obtenemos la ruta del video inicial
        val uri: Uri = Uri.parse( "android.resource://" + packageName +"/raw/intro")
        binding.inicioApp.setVideoURI(uri)
        binding.inicioApp.start()

        //Obtenemos el sonido
        //val sonidoIntro = MediaPlayer.create(this, R.raw.inicio)
        //sonidoIntro.start()

        Handler(Looper.getMainLooper()).postDelayed({
            //Al ser la ventana inicial, donde el usuario no tiene que interactuar
            //Sino que solamente debe de esperar, le quitaremos la animacion que hay
            //Entre ventanas
            val flagIntent = Intent(this, Principal::class.java)
            flagIntent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            startActivity(flagIntent)


        },3000)
    }
}