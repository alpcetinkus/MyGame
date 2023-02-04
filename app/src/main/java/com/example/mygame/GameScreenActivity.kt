package com.example.mygame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import kotlinx.android.synthetic.main.activity_game_screen.*
import java.util.*
import kotlin.concurrent.schedule
import kotlin.math.floor

class GameScreenActivity : AppCompatActivity() {

    //positions
    private var characterX = 0.0f
    private var characterY = 0.0f
    private var bombX = 0.0f
    private var bombY = 0.0f
    private var redX = 0.0f
    private var redY = 0.0f
    private var purpleX = 0.0f
    private var purpleY = 0.0f

    //size
    private var screenWith = 0
    private var screenHeight = 0
    private var characterWith = 0
    private var characterHeight = 0

    //controls
    private var touchControl = false
    private var control = false
    private val timer = Timer()
    private var score = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_screen)

        bomb_ingame.x = -8000.0f
        bomb_ingame.y = -8000.0f
        red_point_ingame.x = -8000.0f
        red_point_ingame.y = -8000.0f
        purple_point_ingame.x = -8000.0f
        purple_point_ingame.y = -8000.0f

        character_ingame.setOnClickListener {
            startActivity(Intent(this@GameScreenActivity, ScoreScreenActivity::class.java))
            finish()
        }
        gameScreen.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {

                if (control) {
                    if (p1?.action == MotionEvent.ACTION_DOWN) {
                        touchControl = true
                    }
                    if (p1?.action == MotionEvent.ACTION_UP) {
                        touchControl = false
                    }
                } else {

                    control = true
                    tapToStart.visibility = View.INVISIBLE
                    characterX = character_ingame.x
                    characterY = character_ingame.y
                    screenWith = gameScreen.width
                    screenHeight = gameScreen.height
                    characterWith = character_ingame.width
                    characterHeight = character_ingame.height

                    timer.schedule(0, 20) {
                        Handler(Looper.getMainLooper()).post {
                            characterMove()
                            move()
                            touchControl()
                        }
                    }
                }
                return true
            }

        })
    }

    fun characterMove() {

        val characterSpeed = screenHeight / 60.0f
        if (touchControl) {
            characterY -= characterSpeed
        } else {
            characterY += characterSpeed
        }

        if (characterY <= 0) {
            characterY = 0.0f
        }

        if (characterY >= screenHeight - characterHeight) {
            characterY = (screenHeight - characterHeight).toFloat()
        }
        character_ingame.y = characterY
    }

    fun move() {

        bombX -= screenWith / 55.0f
        redX -= screenWith / 65.0f
        purpleX -= screenWith / 45.0f

        if (bombX < 0.0f) {
            bombX = screenWith + 20.0f
            bombY = floor(Math.random() * screenHeight).toFloat()
        }
        if (redX < 0.0f) {
            redX = screenWith + 20.0f
            redY = floor(Math.random() * screenHeight).toFloat()
        }
        if (purpleX < 0.0f) {
            purpleX = screenWith + 20.0f
            purpleY = floor(Math.random() * screenHeight).toFloat()
        }
        red_point_ingame.x = redX
        red_point_ingame.y = redY
        purple_point_ingame.x = purpleX
        purple_point_ingame.y = purpleY
        bomb_ingame.x = bombX
        bomb_ingame.y = bombY
    }

    fun touchControl() {
        val redX20 = redX + red_point_ingame.width / 2.0f
        val redY20 = redY + red_point_ingame.height / 2.0f

        if (0.0f <= redX20 && redX20 <= characterWith
            && characterY <= redY20 && redY20 <= characterY + characterHeight) {
            score += 20
            redX = -10.0f
        }

        val purpleX100 = purpleX + purple_point_ingame.width / 2.0f
        val purpleY100 = purpleY + purple_point_ingame.height / 2.0f

        if (0.0f <= purpleX100 && purpleX100 <= characterWith
            && characterY <= purpleY100 && purpleY100 <= characterY + characterHeight) {
            score += 100
            purpleX = -10.0f
        }

        val endBombX = bombX + bomb_ingame.width / 2.0f
        val endBombY = bombY + bomb_ingame.height / 2.0f

        if (0.0f <= endBombX && endBombX <= characterWith
            && characterY <= endBombY && endBombY <= characterY + characterHeight) {

            bombX = -10.0f
            timer.cancel()

            val intent = Intent(this@GameScreenActivity, ScoreScreenActivity::class.java)
            intent.putExtra("score", score)
            startActivity(intent)
            finish()
        }
        score_ingame.text = score.toString()
    }


}