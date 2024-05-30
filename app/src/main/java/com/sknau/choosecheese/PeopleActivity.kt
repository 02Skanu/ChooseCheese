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

class PeopleActivity : AppCompatActivity(){
    private lateinit var binding: ActivityPeopleButtonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPeopleButtonBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val btn: ImageButton = findViewById(R.id.people_back_button)
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
        fragmentList.add(OnePeopleFragment())
        fragmentList.add(TwoPeopleFragment())
        fragmentList.add(ThreePeopleFragment())
        fragmentList.add(FourPeopleFragment())
        fragmentList.add(FivePeopleFragment())

        viewPager.adapter = ViewPagerAdapter(fragmentList, this)
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Log.e("ViewPagerFragment", "Page ${position+1}")
            }
        })
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = "${position + 1}명"
        }.attach()

    }



}