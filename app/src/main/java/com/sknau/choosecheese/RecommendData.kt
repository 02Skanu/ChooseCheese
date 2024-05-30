package com.sknau.choosecheese


data class ChartData(

    val pose_status: Map<String, Number>?,
    val mood_status: Map<String, Number>?,
    val minimum_pose: MinimumPose?,
    val minimum_mood: MinimumMood?,
    val recommend_images: List<String>?
)

data class MinimumPose(
    val pose_count: Int,
    val pose_name: String
)

data class MinimumMood(
    val mood_count: Int,
    val mood_name: String
)