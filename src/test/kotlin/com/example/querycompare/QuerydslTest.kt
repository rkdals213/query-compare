package com.example.querycompare

import com.example.querycompare.domain.QMember.member
import com.example.querycompare.domain.QPenalty.penalty
import com.example.querycompare.domain.QTeam.team
import com.querydsl.jpa.impl.JPAQueryFactory
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class QuerydslTest @Autowired constructor(
    private val query: JPAQueryFactory
) {

    @Test
    fun findTeam() {
        query.selectFrom(team)
            .fetch()
            .forEach { println(it) }
    }

    @Test
    fun findMember() {
        query.selectFrom(member)
            .fetch()
            .forEach { println(it) }
    }

    @Test
    fun findTeamWithoutPenalty() {
        val penaltyMemberIds = query.selectFrom(penalty)
            .fetch()
            .map { it.memberId }

        query.selectFrom(team)
            .innerJoin(member).on(member.team.eq(team))
            .where(
                member.id.notIn(penaltyMemberIds)
            )
            .fetch()
            .forEach { println(it) }
    }
}
