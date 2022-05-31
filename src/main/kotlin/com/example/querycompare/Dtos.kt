package com.example.querycompare

data class TeamMemberCount(
    val teamId: Long,
    val teamName: String,
    val memberCount: Long
)

data class TeamGame(
    val teamId: Long,
    val teamName: String,
    val score: Int
)

data class MemberPenalty(
    val memberId: Long,
    val memberName: String,
    val reason: String
)