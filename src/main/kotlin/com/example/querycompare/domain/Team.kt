package com.example.querycompare.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
class Team(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    var name: String,

    var introduce: String,

    @OneToMany(mappedBy = "team")
    val members: MutableList<Member> = mutableListOf()
) {
    override fun toString(): String {
        return "Team(id=$id, name='$name', introduce='$introduce', members=$members)"
    }
}