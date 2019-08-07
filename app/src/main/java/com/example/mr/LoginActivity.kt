package com.example.mr

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        skipTextView.setOnClickListener{
            afterAnimationView.visibility = GONE
            signupView.visibility = VISIBLE
        }

        skipTextView2.setOnClickListener{
            afterAnimationView.visibility = VISIBLE
            signupView.visibility = GONE
        }

        object : CountDownTimer(2000, 1000) {
            override fun onFinish() {
                bookITextView.visibility = View.GONE
                loadingProgressBar.visibility = View.GONE
                rootView.setBackgroundColor(ContextCompat.getColor(this@LoginActivity, R.color.colorSplashText))
                bookIconImageView.setImageResource(R.drawable.background_color_book)
                startAnimation()
            }
            override fun onTick(p0: Long) {}
        }.start()

        loginButton.setOnClickListener{
            var email = emailEditText.text.toString()
            var passwd = passwordEditText.text.toString()

            if(email.trim()=="" || passwd.trim()==""){
                Toast.makeText(this, "공백이 존재하면 안됩니다", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val auth = FirebaseAuth.getInstance()
            auth.signInWithEmailAndPassword(email, passwd)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        var user = auth.currentUser
                        if(user!!.isEmailVerified) {
                            Toast.makeText(this, "로그인 성공", Toast.LENGTH_LONG).show()
                            val nextIntent = Intent(this, MainActivity::class.java)
                            startActivity(nextIntent)
                        } else {
                            Toast.makeText(this, "이메일 인증을 완료해주세요", Toast.LENGTH_LONG).show()
                            auth.signOut()
                        }
                    }else{
                        Toast.makeText(this, "로그인 실패", Toast.LENGTH_LONG).show()
                    }
                }
        }


        loginButton2.setOnClickListener{
            var email = emailEditText2.text.toString()
            var passwd = passwordEditText2.text.toString()

            if(email.trim()=="" || passwd.trim()==""){
                Toast.makeText(this, "공백이 존재하면 안됩니다", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }


            var domain = email.substring(email.lastIndexOf("@"))
            if(domain == "@pusan.ac.kr")
                doSignUp(email, passwd)
            else
                Toast.makeText(this, "부산대학교 메일만 사용가능합니다\nEX) email@pusan.ac.kr", Toast.LENGTH_LONG).show()
        }
    }

    fun doSignUp(email: String, passwd: String){
        val auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(email, passwd)
            .addOnCompleteListener{ task->
                if(task.isCanceled)
                    Toast.makeText(this, "회원가입 실패", Toast.LENGTH_LONG).show()
                else if(task.isSuccessful)
                    auth.currentUser!!.sendEmailVerification()
                    Toast.makeText(this, "회원가입 성공\n인증 메일이 발송되었습니다 확인해주세요!!", Toast.LENGTH_LONG).show()
            }
    }


    private fun startAnimation() {
        bookIconImageView.animate().apply {
            x(50f)
            y(100f)
            duration = 1000
        }.setListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {
            }
            override fun onAnimationEnd(p0: Animator?) {
                afterAnimationView.visibility = VISIBLE
            }
            override fun onAnimationCancel(p0: Animator?) {
            }
            override fun onAnimationStart(p0: Animator?) {
            }
        })
    }
}