package com.example.querycompare.domain

import javax.persistence.*

@Entity
class Game(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    var score: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var team: Team
) {
}