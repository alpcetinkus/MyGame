package com.example.mygame

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_score_screen.*

class ScoreScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score_screen)

        val score = intent.getIntExtra("score", 0)
        score_end.text = score.toString()

        val sp = getSharedPreferences("result", Context.MODE_PRIVATE)
        val highScore = sp.getInt("highScore",0)

        if (score > highScore) {
            val editor = sp.edit()
            editor.putInt("highScore",score)
            editor.commit()
            highScore_end.text = score.toString()
        } else {
            highScore_end.text = highScore.toString()
        }

        again_game_btn.setOnClickListener {
            startActivity(Intent(this@ScoreScreenActivity,MainActivity::class.java))
        }
    }
}