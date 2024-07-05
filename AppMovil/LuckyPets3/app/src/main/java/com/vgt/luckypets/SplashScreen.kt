package com.vgt.luckypets

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vgt.luckypets.activity.LoginActivity
import com.vgt.luckypets.databinding.SplashScreenBinding

class SplashScreen : AppCompatActivity() {
    private lateinit var binding: SplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val uri: Uri = Uri.parse("android.resource://" + packageName + "/raw/intro")
        binding.inicioApp.setVideoURI(uri)
        binding.inicioApp.start()

        binding.inicioApp.setOnCompletionListener {
            startMainActivity()
        }
    }

    override fun onPause() {
        super.onPause()
        binding.inicioApp.pause()
    }

    override fun onResume() {
        super.onResume()
        binding.inicioApp.start()
    }

    override fun onDestroy() {
        binding.inicioApp.stopPlayback()
        super.onDestroy()
    }

    private fun startMainActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

}
