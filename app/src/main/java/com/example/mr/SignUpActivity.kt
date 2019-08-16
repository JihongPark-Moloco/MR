package com.example.mr

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.sign_up.*


class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up)

        back.setOnClickListener {
            finish()
        }

        signInButton.setOnClickListener {
            var email = email_input.text.toString().trim()
            var passwd = passwd_input.text.toString().trim()
            var re_passwd = re_passwd_input.text.toString().trim()

            if (email == "" || passwd == "" || re_passwd == "") {
                Toast.makeText(this, "공백이 존재하면 안됩니다", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (passwd != re_passwd) {
                Toast.makeText(this, "패스워드가 일치하지 않습니다", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val auth = FirebaseAuth.getInstance()
            auth.createUserWithEmailAndPassword(email, passwd)
                .addOnCompleteListener { task ->
                    if (task.isCanceled)
                        Toast.makeText(this, "회원가입 실패", Toast.LENGTH_LONG).show()
                    else if (task.isSuccessful)
                        auth.currentUser!!.sendEmailVerification()
                    Toast.makeText(this, "회원가입 성공\n인증 메일이 발송되었습니다 확인해주세요!!", Toast.LENGTH_LONG).show()
                }

        }
    }
}