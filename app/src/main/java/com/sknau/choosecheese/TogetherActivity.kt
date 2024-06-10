package com.sknau.choosecheese

import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class TogetherActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TogetherAdapter
    private val imageUrlList: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_together)

        val btn: ImageButton = findViewById(R.id.together_back_button)
        btn.setOnClickListener {
            finish()  // MainActivity로 돌아가는 대신, 현재 액티비티 종료
        }

        recyclerView = findViewById(R.id.together_recyclerview)
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)

        adapter = TogetherAdapter(imageUrlList)
        recyclerView.adapter = adapter

        val imageUrls = intent.getStringArrayExtra("imageUrls") ?: arrayOf()
        loadImages(imageUrls)
    }

    private fun loadImages(imageUrls: Array<String>) {
        imageUrlList.clear()
        imageUrlList.addAll(imageUrls)
        adapter.notifyDataSetChanged()
        Log.d("TogetherActivity", "이미지 로드 성공: $imageUrls")
    }
}
