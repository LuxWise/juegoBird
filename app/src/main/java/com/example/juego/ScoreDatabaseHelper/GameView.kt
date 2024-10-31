package com.example.juego

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import kotlin.random.Random

class GameView(context: Context) : SurfaceView(context), Runnable {
    private val paint: Paint = Paint().apply {
        color = Color.BLUE
        style = Paint.Style.FILL
    }
    private var isPlaying: Boolean = false
    private lateinit var thread: Thread
    private val bird: Bird = Bird(100f, 300f) // Inicializa el pájaro
    private val obstacles: MutableList<Obstacle> = mutableListOf()
    private var obstacleTimer: Long = 0 // Temporizador para generar obstáculos

    // Añade un Bitmap para la imagen de fondo
    private val backgroundBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.leonardo_anime_xl_when_designing_a_pixel_art_game_simple_i_nee_1)

    init {
        setWillNotDraw(false)
    }

    override fun run() {
        while (isPlaying) {
            update() // Actualiza la lógica del juego
            draw()
            controlFPS()
            bird.fall() // Actualiza la posición del pájaro
        }
    }

    private fun update() {
        // Genera nuevos obstáculos
        if (System.currentTimeMillis() - obstacleTimer > 2000) { // Genera un nuevo obstáculo cada 2 segundos
            val height = Random.nextInt(100, 400).toFloat() // Altura aleatoria del obstáculo
            obstacles.add(Obstacle(width.toFloat(), height)) // Agrega un nuevo obstáculo
            obstacleTimer = System.currentTimeMillis() // Reinicia el temporizador
        }

        // Actualiza la posición de los obstáculos
        for (obstacle in obstacles) {
            obstacle.update()
        }

        // Elimina los obstáculos que se han salido de la pantalla
        if (obstacles.isNotEmpty() && obstacles[0].x < -50) {
            obstacles.removeAt(0)
        }
    }

    private fun draw() {
        var canvas: Canvas? = null
        val holder: SurfaceHolder = holder
        try {
            canvas = holder.lockCanvas()
            if (canvas != null) {
                synchronized(holder) {
                    // Dibuja el fondo
                    canvas.drawBitmap(backgroundBitmap, 0f, 0f, null)

                    bird.draw(canvas) // Dibuja el pájaro

                    // Dibuja los obstáculos
                    for (obstacle in obstacles) {
                        obstacle.draw(canvas)
                    }
                }
            }
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } finally {
            canvas?.let { holder.unlockCanvasAndPost(it) }
        }
    }

    private fun controlFPS() {
        try {
            Thread.sleep(16) // Aproximadamente 60 FPS
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            bird.jump() // Llama al método jump() cuando se toca la pantalla
        }
        return true
    }

    fun start() {
        isPlaying = true
        thread = Thread(this)
        thread.start()
    }

    fun stop() {
        isPlaying = false
        try {
            thread.join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}
