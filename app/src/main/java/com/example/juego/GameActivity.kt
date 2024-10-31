package com.example.juego

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.util.Log

class GameActivity : AppCompatActivity() {
    private lateinit var gameView: GameView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        gameView = GameView(this)
        setContentView(gameView)
        gameView.start() // Asegúrate de que esto se llame
    }

    override fun onPause() {
        super.onPause()
        gameView.stop() // Detener el juego cuando la actividad se pausa
    }

    override fun onResume() {
        super.onResume()
        gameView.start() // Reinicia el juego si es necesario
    }
}
