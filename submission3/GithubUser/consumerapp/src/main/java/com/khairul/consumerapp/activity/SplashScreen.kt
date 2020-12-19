package com.khairul.consumerapp.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.khairul.consumerapp.R
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreen : AppCompatActivity() {
    companion object {
        private const val SplashTimeOut:Long = 3000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val animation1 = AnimationUtils.loadAnimation(this, R.anim.top_anim)
        val animation2 = AnimationUtils.loadAnimation(this, R.anim.but_anim)

        imageView.startAnimation(animation1)
        imageView2.startAnimation(animation2)
        textView.startAnimation(animation1)
        textView2.startAnimation(animation2)

        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, SplashTimeOut)
    }


}
