package com.example.juego

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class Bird(var x: Float, var y: Float) {
    private val paint: Paint = Paint().apply {
        color = Color.BLUE
        style = Paint.Style.FILL
    }
    private val width: Float = 50f
    private val height: Float = 50f
    private var velocity: Float = 0f // Velocidad de caída

    fun draw(canvas: Canvas) {
        canvas.drawRect(x, y, x + width, y + height, paint)
    }

    fun fall() {
        velocity += 1f // Aumenta la velocidad de caída
        y += velocity // Aplica la velocidad a la posición vertical
        if (y > 600) { // Limita la caída (ajusta según tu pantalla)
            y = 600f
        }
    }

    // Método para hacer saltar al pájaro
    fun jump() {
        velocity = -20f // Ajusta esta velocidad para controlar la altura del salto
    }

    fun getBounds(): FloatArray {
        return floatArrayOf(x, y, x + width, y + height)
    }
}
