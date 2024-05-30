package com.sknau.choosecheese

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sknau.choosecheese.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Response

class LogInActivity: AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnLogin.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)




        binding.btnLogin.setOnClickListener {
            val id = binding.loginEdittextId.text.toString().trim() //trim은 공백 제거해줌
            val pw = binding.loginEdittextPw.text.toString().trim()

            saveData(id,pw) //db(shared preference)에 자동저장 (자동 로그인 용)

            //백엔드 통신
            val api = LoginApiService.create()
            val data = LoginData(id, pw)

            api.userLogin(data).enqueue(object : retrofit2.Callback<LoginBackendResponse> {
                override fun onResponse(
                    call: Call<LoginBackendResponse>,
                    response: Response<LoginBackendResponse>
                ) {
                    Log.d("로그인 통신 성공", response.toString())
                    Log.d("로그인 통신 성공", response.body().toString())
                    Log.d("토큰 저장", response.body()?.access_token.toString())

                    val prefTOKEN = getSharedPreferences("accessTOKEN", MODE_PRIVATE)
                    val editTOKEN = prefTOKEN.edit()
                    editTOKEN.putString("accessToken",response.body()?.access_token.toString())
                    editTOKEN.apply()

                    when(response.code()){
                        200 -> {
                            saveData(id,pw)
                            val intent = Intent(this@LogInActivity, MainActivity::class.java)
                            startActivity(intent)
                        }
                        401 -> Toast.makeText(this@LogInActivity, "로그인 실패 : 아이디나 비밀번호가 올바르지 않습니다.",
                            Toast.LENGTH_LONG).show()
                        500 -> Toast.makeText(this@LogInActivity, "로그인 실패 : 서버 오류", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<LoginBackendResponse>, t: Throwable){
                    //실패
                    Log.d("로그인 통신 실패",t.message.toString())
                    Log.d("로그인 통신 실패","fail")

                }
            })
        }

    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val imm: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        return super.dispatchTouchEvent(ev)
    }

    fun saveData(id : String, pw: String){
        val prefID = getSharedPreferences("userID", MODE_PRIVATE)
        val prefPW = getSharedPreferences("userPW", MODE_PRIVATE)
        val editID = prefID.edit()
        val editPW = prefID.edit() // 수정모드
        editID.putString("username", id) // 값 넣기
        editPW.putString("password", pw)
        editID.apply()
        editPW.apply() //save
        Log.d("로그인 데이터", "saved")
    }


}