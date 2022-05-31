package com.example.querycompare

import com.example.querycompare.domain.Game
import com.example.querycompare.domain.Member
import com.example.querycompare.domain.Penalty
import com.example.querycompare.domain.Team
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.from.fetch
import com.linecorp.kotlinjdsl.querydsl.from.join
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.selectQuery
import com.linecorp.kotlinjdsl.spring.data.singleQuery
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class QueryJdslTest @Autowired constructor(
    private val queryFactory: SpringDataQueryFactory
) {
    @Test
    fun findTeamOne() {
        queryFactory.singleQuery<Team> {
            select(entity(Team::class))
            from(entity(Team::class))
            where(col(Team::id).equal(1L))
        }
            .run { println(this) }
    }

    @Test
    fun findTeam() {
        queryFactory.selectQuery<Team> {
            select(entity(Team::class))
            from(entity(Team::class))
        }
            .resultList
            .forEach { println(it) }
    }

    @Test
    fun findTeamFetch() {
        queryFactory.selectQuery<Team> {
            selectDistinct(entity(Team::class))
            from(entity(Team::class))
            fetch(Team::members)
        }
            .resultList
            .forEach { println(it) }
    }

    @Test
    fun findMember() {
        queryFactory.selectQuery<Member> {
            select(entity(Member::class))
            from(entity(Member::class))
        }
            .resultList
            .forEach { println(it) }
    }

    @Test
    fun findTeamMemberCount() {
        queryFactory.selectQuery<TeamMemberCount> {
            selectMulti(
                col(Team::id),
                col(Team::name),
                count(col(Member::id))
            )
            join(Team::members)
            from(entity(Team::class))
            groupBy(col(Team::id))
        }
            .resultList
            .forEach { println(it) }
    }

    @Test
    fun findTeamGame() {
        queryFactory.selectQuery<TeamGame> {
            selectMulti(
                col(Team::id),
                col(Team::name),
                col(Game::score)
            )
            join(Game::team)
            from(entity(Game::class))
        }
            .resultList
            .forEach { println(it) }
    }

    @Test
    fun findMemberPenalty() {
        queryFactory.selectQuery<MemberPenalty> {
            selectMulti(
                col(Member::id),
                col(Member::name),
                col(Penalty::reason)
            )
            join(Penalty::class, on {
                col(Penalty::memberId).equal(col(Member::id))  // cross join 발생
            })
            from(entity(Member::class))
        }
            .resultList
            .forEach { println(it) }
    }

    @Test
    fun findTeamWithPenalty() {
        val penaltyIds = queryFactory.selectQuery<Long> {
            select(col(Penalty::memberId))
            from(entity(Penalty::class))
        }.resultList

        queryFactory.selectQuery<Team> {
            select(entity(Team::class))
            join(Member::team)
            from(entity(Member::class))
            where(
                and(
                    col(Member::id).`in`(penaltyIds) // not in 절이 없음
                )
            )
        }
            .resultList
            .forEach { println(it) }
    }

}
