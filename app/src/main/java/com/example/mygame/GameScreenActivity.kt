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

class GameScreenActivity : AppCompatActivity() {

    //positions
    private var characterX = 0.0f
    private var characterY = 0.0f

    //size
    private var screenWith = 0
    private var screenHeight = 0
    private var characterWith = 0
    private var characterHeight = 0

    //controls
    private var touchControl = false
    private var control = false
    private val timer = Timer()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_screen)

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
                    characterX = character_ingame.x
                    characterY = character_ingame.y
                    screenWith = gameScreen.width
                    screenHeight = gameScreen.height
                    characterWith = character_ingame.width
                    characterHeight = character_ingame.height

                    timer.schedule(0, 20) {
                        Handler(Looper.getMainLooper()).post {
                            characterMove()
                        }
                    }
                }
                return true
            }

        })
    }

    fun characterMove() {
        if (touchControl) {
            characterY -= 20.0f
        } else {
            characterY += 20.0f
        }

        if (characterY <= 0) {
            characterY = 0.0f
        }

        if (characterY >= screenHeight - characterHeight) {
            characterY = (screenHeight - characterHeight).toFloat()
        }
        character_ingame.y = characterY
    }
}