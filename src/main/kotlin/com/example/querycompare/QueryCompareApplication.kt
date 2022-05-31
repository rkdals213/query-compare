package com.example.querycompare

import com.example.querycompare.domain.Game
import com.example.querycompare.domain.Member
import com.example.querycompare.domain.Penalty
import com.example.querycompare.domain.Team
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.annotation.PostConstruct
import javax.persistence.EntityManager

@SpringBootApplication
class QueryCompareApplication

fun main(args: Array<String>) {
    runApplication<QueryCompareApplication>(*args)
}

@Component
class InitDb {
    @Autowired
    private lateinit var initService: InitService

    @PostConstruct
    fun init() {
        initService.dbInit()
    }

    @Component
    @Transactional
    internal class InitService {
        @Autowired
        private lateinit var em: EntityManager

        fun dbInit() {
            val team1 = Team(0, "Team1", "Introduce")
            em.persist(team1)

            val member1 = Member(0, "Member1", "test", team1)
            team1.members.add(member1)
            em.persist(member1)
            val member2 = Member(0, "Member2", "test", team1)
            team1.members.add(member2)
            em.persist(member2)

            val team2 = Team(0, "Team2", "Introduce")
            em.persist(team2)

            val member3 = Member(0, "Member3", "test", team2)
            team1.members.add(member3)
            em.persist(member3)
            val member4 = Member(0, "Member4", "test", team2)
            team1.members.add(member4)
            em.persist(member4)

            val game1 = Game(0, 10, team1)
            em.persist(game1)
            val game2 = Game(0, 3, team1)
            em.persist(game2)
            val game3 = Game(0, 7, team1)
            em.persist(game3)

            val penalty1 = Penalty(0, "탈주함", 1L)
            em.persist(penalty1)
            val penalty2 = Penalty(0, "탈주함", 2L)
            em.persist(penalty2)
        }

    }
}