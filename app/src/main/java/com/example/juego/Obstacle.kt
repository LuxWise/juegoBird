package com.example.juego

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class Obstacle(var x: Float, var height: Float) { // Cambia 'val' a 'var' en 'x'
    private val paint: Paint = Paint().apply {
        color = Color.GREEN // Color de los obstáculos
    }
    private val width: Float = 50f // Ancho de los obstáculos

    fun draw(canvas: Canvas) {
        // Dibuja el obstáculo
        canvas.drawRect(x, 0f, x + width, height, paint) // Dibuja el obstáculo superior
        canvas.drawRect(x, height + 300f, x + width, canvas.height.toFloat(), paint) // Dibuja el obstáculo inferior
    }

    fun update() {
        x -= 5 // Mueve el obstáculo a la izquierda
    }

    fun getBounds(): FloatArray {
        return floatArrayOf(x, 0f, x + width, height + 200f) // Devuelve los límites del obstáculo
    }
}
