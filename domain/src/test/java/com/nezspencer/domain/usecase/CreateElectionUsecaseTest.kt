package com.nezspencer.domain.usecase

import com.nezspencer.domain.entity.Contestant
import com.nezspencer.domain.entity.Election
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.util.*

class CreateElectionUsecaseTest {

    @Test
    fun shouldNotCreateElectionIfEmailToBeUsedAsNodeContains_A_Dot() {
        val reposStub = FakeElectionRepo()
        val testEmail = "nuhiara@gmail.com"
        val title = "Office of the president"
        val electoralSeat = "President"
        val contestants = mutableListOf(Contestant("s", "sonc"))
        val creator = "Eze"
        val cal = Calendar.getInstance()
        cal.add(Calendar.YEAR, 1)
        val date = cal.timeInMillis
        val userUsecase = CreateElectionUsecase(reposStub,
                Election(title,
                        electoralSeat,
                        contestants,
                        date,
                        creator,
                        testEmail,
                        "djdjdjd"),
                testEmail)

        runBlocking { assert(!userUsecase()) }
    }

    @Test
    fun shouldNotCreateElectionIfTitleIsEmptyString() {
        val reposStub = FakeElectionRepo()
        val testEmail = "nuhiara@gmail.com"
        val title = ""
        val electoralSeat = "President"
        val contestants = mutableListOf(Contestant("s", "sonc"))
        val creator = "Eze"
        val cal = Calendar.getInstance()
        cal.add(Calendar.YEAR, 1)
        val date = cal.timeInMillis
        val userUsecase = CreateElectionUsecase(reposStub,
                Election(title,
                        electoralSeat,
                        contestants,
                        date,
                        creator,
                        testEmail,
                        "djdjdjd"),
                testEmail.replace(".", ","))

        runBlocking { assert(!userUsecase()) }
    }

    @Test
    fun shouldNotCreateElectionIfElectoralSeatIsEmpty() {
        val reposStub = FakeElectionRepo()
        val testEmail = "nuhiara@gmail.com"
        val title = "Office of the president"
        val electoralSeat = ""
        val contestants = mutableListOf(Contestant("s", "sonc"))
        val creator = "Eze"
        val cal = Calendar.getInstance()
        cal.add(Calendar.YEAR, 1)
        val date = cal.timeInMillis
        val userUsecase = CreateElectionUsecase(reposStub,
                Election(title,
                        electoralSeat,
                        contestants,
                        date,
                        creator,
                        testEmail,
                        "djdjdjd"),
                testEmail.replace(".", ","))

        runBlocking { assert(!userUsecase()) }
    }

    @Test
    fun shouldNotCreateElectionIfContestantListIsEmpty() {
        val reposStub = FakeElectionRepo()
        val testEmail = "nuhiara@gmail.com"
        val title = "Office of the president"
        val electoralSeat = "President"
        val contestants = mutableListOf<Contestant>()
        val creator = "Eze"
        val cal = Calendar.getInstance()
        cal.add(Calendar.YEAR, 1)
        val date = cal.timeInMillis
        val userUsecase = CreateElectionUsecase(reposStub,
                Election(title,
                        electoralSeat,
                        contestants,
                        date,
                        creator,
                        testEmail,
                        "djdjdjd"),
                testEmail.replace(".", ","))

        runBlocking { assert(!userUsecase()) }
    }

    @Test
    fun shouldNotCreateElectionIfCreatorNameIsEmpty() {
        val reposStub = FakeElectionRepo()
        val testEmail = "nuhiara@gmail.com"
        val title = "Office of the president"
        val electoralSeat = "President"
        val contestants = mutableListOf(Contestant("s", "sonc"))
        val creator = ""
        val cal = Calendar.getInstance()
        cal.add(Calendar.YEAR, 1)
        val date = cal.timeInMillis
        val userUsecase = CreateElectionUsecase(reposStub,
                Election(title,
                        electoralSeat,
                        contestants,
                        date,
                        creator,
                        testEmail,
                        "djdjdjd"),
                testEmail.replace(".", ","))

        runBlocking { assert(!userUsecase()) }
    }

    @Test
    fun shouldNotCreateElectionIfCreatorEmailIsEmpty() {
        val reposStub = FakeElectionRepo()
        val testEmail = "nuhiara@gmail.com"
        val title = "Office of the president"
        val electoralSeat = "President"
        val contestants = mutableListOf(Contestant("s", "sonc"))
        val creator = "Eze"
        val cal = Calendar.getInstance()
        cal.add(Calendar.YEAR, 1)
        val date = cal.timeInMillis
        val userUsecase = CreateElectionUsecase(reposStub,
                Election(title,
                        electoralSeat,
                        contestants,
                        date,
                        creator,
                        "",
                        "djdjdjd"),
                testEmail.replace(".", ","))

        runBlocking { assert(!userUsecase()) }
    }


    @Test
    fun shouldNotCreateElectionIfDateisInThePastOrCurrentDate() {
        val reposStub = FakeElectionRepo()
        val testEmail = "nuhiara@gmail.com"
        val title = "Office of the president"
        val electoralSeat = "President"
        val contestants = mutableListOf(Contestant("s", "sonc"))
        val creator = "Eze"
        val date = Date().time
        val userUsecase = CreateElectionUsecase(reposStub,
                Election(title,
                        electoralSeat,
                        contestants,
                        date,
                        creator,
                        testEmail,
                        "djdjdjd"),
                testEmail.replace(".", ","))

        runBlocking { assert(!userUsecase()) }
    }

    @Test
    fun shouldCreateElectionWhenAllDetailsAreSupplied() {
        val reposStub = FakeElectionRepo()
        val testEmail = "nuhiara@gmail.com"
        val title = "Office of the president"
        val electoralSeat = "President"
        val contestants = mutableListOf(Contestant("s", "sonc"))
        val creator = "Eze"
        val cal = Calendar.getInstance()
        cal.add(Calendar.YEAR, 1)
        val date = cal.timeInMillis
        val userUsecase = CreateElectionUsecase(reposStub,
                Election(title,
                        electoralSeat,
                        contestants,
                        date,
                        creator,
                        testEmail,
                        "djdjdjd"),
                testEmail.replace(".", ","))

        runBlocking { assert(userUsecase()) }
    }

    @Test
    fun shouldNotPostVoteWhenNoContestantIsSelected() {
        val repo = FakeElectionRepo()
        val testEmail = "nuhiara@gmail.com"
        val contestantId = ""
        val cal = Calendar.getInstance()
        cal.add(Calendar.YEAR, 1)
        val date = cal.timeInMillis
        val election = Election(
                "test election",
                "test",
                mutableListOf(),
                date,
                "me",
                testEmail,
                "id1"

        )
        val voteUsecase = VoteContestantUsecase(repo, contestantId, testEmail, election)
        runBlocking {
            assert(!voteUsecase())
        }
    }

    @Test
    fun shouldNotVoteIfElectionIdIsEmpty() {
        val repo = FakeElectionRepo()
        val testEmail = "nuhiara@gmail.com"
        val contestantId = "23"
        val electionId = ""
        val cal = Calendar.getInstance()
        cal.add(Calendar.YEAR, 1)
        val date = cal.timeInMillis
        val election = Election(
                "test election",
                "test",
                mutableListOf<Contestant>(),
                date,
                "me",
                testEmail,
                electionId

        )
        val voteUsecase = VoteContestantUsecase(repo, contestantId, testEmail, election)
        runBlocking {
            assert(!voteUsecase())
        }
    }

    @Test
    fun shouldNotVoteIfEmailIsEmpty() {
        val repo = FakeElectionRepo()
        val testEmail = ""
        val contestantId = "23"
        val electionId = "1"
        val cal = Calendar.getInstance()
        cal.add(Calendar.YEAR, 1)
        val date = cal.timeInMillis
        val election = Election(
                "test election",
                "test",
                mutableListOf(),
                date,
                "me",
                "e@g.com",
                electionId

        )
        val voteUsecase = VoteContestantUsecase(repo, contestantId, testEmail, election)
        runBlocking {
            assert(!voteUsecase())
        }
    }

    @Test
    fun shouldNotPostVoteIfElectionTitleFieldIsEmpty() {
        val repo = FakeElectionRepo()
        val testEmail = "nuhiara@gmail.com"
        val contestantId = "23"
        val electionId = "2"
        val cal = Calendar.getInstance()
        cal.add(Calendar.YEAR, 1)
        val date = cal.timeInMillis
        val election = Election(
                "",
                "test",
                mutableListOf(),
                date,
                "me",
                testEmail,
                electionId

        )
        val voteUsecase = VoteContestantUsecase(repo, contestantId, testEmail, election)
        runBlocking {
            assert(!voteUsecase())
        }
    }

    @Test
    fun shouldNotPostVoteIfElectoralSeatFieldIsEmpty() {
        val repo = FakeElectionRepo()
        val testEmail = "nuhiara@gmail.com"
        val contestantId = "23"
        val electionId = "2"
        val cal = Calendar.getInstance()
        cal.add(Calendar.YEAR, 1)
        val date = cal.timeInMillis
        val election = Election(
                "test election",
                "",
                mutableListOf(),
                date,
                "me",
                testEmail,
                electionId

        )
        val voteUsecase = VoteContestantUsecase(repo, contestantId, testEmail, election)
        runBlocking {
            assert(!voteUsecase())
        }
    }

    @Test
    fun shouldPostVoteIfAllParametersAreValid() {
        val repo = FakeElectionRepo()
        val testEmail = "nuhiara@gmail.com"
        val contestantId = "23"
        val electionId = "2"
        val cal = Calendar.getInstance()
        cal.add(Calendar.YEAR, 1)
        val date = cal.timeInMillis
        val election = Election(
                "test election",
                "test",
                mutableListOf(),
                date,
                "me",
                testEmail,
                electionId

        )
        val voteUsecase = VoteContestantUsecase(repo, contestantId, testEmail, election)
        runBlocking {
            assert(voteUsecase())
        }
    }
}