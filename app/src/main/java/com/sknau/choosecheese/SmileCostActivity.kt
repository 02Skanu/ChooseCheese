package com.sknau.choosecheese

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.sknau.choosecheese.databinding.ActivitySmileCostBinding

class SmileCostActivity : AppCompatActivity() {

    private lateinit var cardImg: ImageView
    private lateinit var binding: ActivitySmileCostBinding
    private var smileUrl: String? = null
    private var misoPoint: Int = 0
    private var count: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySmileCostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cardImg = findViewById(R.id.cardImg) // cardImg 초기화

        val button: ImageButton = findViewById(R.id.cost_back_button)
        button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val clickResponseData: ClickResponseData? = intent.getParcelableExtra("clickResponseData")
        clickResponseData?.let {
            smileUrl = it.smile_url
            misoPoint = it.miso_point
            Log.d("미소확인",misoPoint.toString())
            showOriginalImage(it.original_url)
        }

        cardImg.setOnClickListener { flipAnimation() }
    }

    private fun showOriginalImage(imageUrl: String?) {
        imageUrl?.let {
            Glide.with(this)
                .load(it)
                .placeholder(R.drawable.placeholder_image)
                .into(cardImg)
        }
    }

    private fun flipAnimation() {
        val oa1 = ObjectAnimator.ofFloat(cardImg, "scaleX", 1f, 0f)
        val oa2 = ObjectAnimator.ofFloat(cardImg, "scaleX", 0f, 1f)

        oa1.interpolator = DecelerateInterpolator()
        oa2.interpolator = AccelerateDecelerateInterpolator()

        oa1.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                smileUrl?.let {
                    Glide.with(this@SmileCostActivity)
                        .load(it)
                        .placeholder(R.drawable.placeholder_image)
                        .into(cardImg)
                    if (count % 2 == 0)
                        showMisoPoint()
                    else
                        hideMisoPoint()

                    count += 1
                }
                oa2.start()
            }
        })
        oa1.start()
        oa1.duration = 300
        oa2.duration = 300
    }


    private fun showMisoPoint() {
        val misoPointTextView: TextView = findViewById(R.id.miso_point_text_view)
        misoPointTextView.text = getString(R.string.miso_point, misoPoint)
        misoPointTextView.visibility = View.VISIBLE
    }

    private fun hideMisoPoint() {
        val misoPointTextView: TextView = findViewById(R.id.miso_point_text_view)

        misoPointTextView.visibility = View.GONE
    }
}
