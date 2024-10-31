package com.example.juego

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.util.Log

class MainActivity : AppCompatActivity() {
    private lateinit var scoreDatabaseHelper: ScoreDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        scoreDatabaseHelper = ScoreDatabaseHelper(this)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnStartGame: Button = findViewById(R.id.btnStartGame)
        btnStartGame.setOnClickListener {
            Log.d("MainActivity", "Start Game button clicked")
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }

        val btnHighScore: Button = findViewById(R.id.btnHighScore)
        btnHighScore.setOnClickListener {
            val highScore = scoreDatabaseHelper.getHighScore()
            Toast.makeText(this, "Puntaje Máximo: $highScore", Toast.LENGTH_LONG).show()
        }
    }
}
