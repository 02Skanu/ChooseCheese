package com.sknau.choosecheese

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.sknau.choosecheese.databinding.ActivityPeopleButtonBinding
import com.sknau.choosecheese.databinding.ActivityPoseBinding

class PoseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPoseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPoseBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val btn: ImageButton = findViewById(R.id.pose_back_button)
        btn.setOnClickListener{
            val intent = Intent(this,RecommendActivity::class.java)
            startActivity(intent)
        }
        initView()
    }
    private fun initView() {
        val viewPager = binding.buttonViewpager
        val tabLayout = binding.buttonTab

        Log.d("ViewPagerDebug", "viewPager: $viewPager")
        Log.d("ButtonViewpagerDebug", "buttonViewpager: ${binding.buttonViewpager}")

        val fragmentList = ArrayList<Fragment>()
        fragmentList.add(Pose1Fragment())
        fragmentList.add(Pose2Fragment())
        fragmentList.add(Pose3Fragment())
        fragmentList.add(Pose4Fragment())
        fragmentList.add(Pose5Fragment())
        fragmentList.add(Pose6Fragment())


        viewPager.adapter = ViewPagerAdapter(fragmentList, this)
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Log.e("ViewPagerFragment", "Page ${position+1}")
            }
        })
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when(position){
                0 -> tab.text = "사랑스러운"
                1 -> tab.text = "즐거운"
                2 -> tab.text = "귀여운"
                3 -> tab.text = "장난스러운"
                4 -> tab.text = "화목한"
                5 -> tab.text = "무서움"
            }
        }.attach()

    }

}