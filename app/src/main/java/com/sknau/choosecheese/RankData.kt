package com.sknau.choosecheese

data class RankItem(
    val emoji: String,
    val rank: Int,
    val username: String,
    val total_miso: Int
)

data class RankData(
    val ranking: List<RankItem>,
    val my_total_miso: List<Int>,
    val my_username: String
)
