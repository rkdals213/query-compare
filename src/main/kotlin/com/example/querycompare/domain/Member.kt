package com.example.querycompare.domain

import javax.persistence.*

@Entity
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    var name: String,

    var introduce: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var team: Team
) {
    override fun toString(): String {
        return "Member(id=$id, name='$name', introduce='$introduce')"
    }
}