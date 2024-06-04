package com.sknau.choosecheese

data class RankItem(
    val rank: String?,
    val picture: String?,
    val name: String?,
    val score: Int?
)

data class RankData(
    val rank: List<RankItem>,
    val total_score: Int?
)
