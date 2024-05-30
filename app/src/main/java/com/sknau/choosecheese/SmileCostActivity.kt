package com.sknau.choosecheese


import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.sknau.choosecheese.databinding.ActivitySmileCostBinding
import javax.security.auth.callback.Callback

class SmileCostActivity: AppCompatActivity() {

    private lateinit var cardImg: ImageView
    private lateinit var binding: ActivitySmileCostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySmileCostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("accessTOKEN", MODE_PRIVATE)
        val authToken = sharedPreferences.getString("accessToken", null) ?: ""

        val button: ImageButton = findViewById(R.id.cost_back_button)

        button.setOnClickListener {
            val intent = Intent(this, RecommendActivity::class.java)
            startActivity(intent)
        }
        val imgUrl = getImages(authToken)


        cardImg = findViewById<ImageView>(R.id.cardImg)
        cardImg.setOnClickListener{ flipAnimation() }

    }

    private fun getImages(authToken:String){
        val retrofit = LogicApiClient.getClient(authToken)
            .create(SmileCostApiService::class.java)
        retrofit.getImages().enqueue(object : Callback<SmileCostData>{

        })


    }

    private fun flipAnimation() {
        val oa1 = ObjectAnimator.ofFloat(cardImg, "scaleX",1f,0f)
        val oa2 = ObjectAnimator.ofFloat(cardImg,"scaleX",0f,1f)

        oa1.interpolator = DecelerateInterpolator()
        oa2.interpolator = AccelerateDecelerateInterpolator()

        oa1.addListener(object : AnimatorListenerAdapter(){
            override fun onAnimationEnd(animation: Animator){
                super.onAnimationEnd(animation)
                if (cardImg.drawable.constantState ==
                    resources.getDrawable(R.drawable.icon_rank_none).constantState){
                    cardImg.setImageResource(R.drawable.icon_rank_selected)
                }else{
                    cardImg.setImageResource(R.drawable.icon_rank_none)
                }
                oa2.start()
            }
        })
        oa1.start()
        oa1.setDuration(1000)
        oa2.setDuration(1000)
    }
}