package com.example.smlr.model.repositories

import com.example.smlr.model.AbstractRepositoryTest
import com.example.smlr.model.Link
import com.github.springtestdbunit.annotation.DatabaseOperation
import com.github.springtestdbunit.annotation.DatabaseSetup
import com.github.springtestdbunit.annotation.DatabaseTearDown
import org.hamcrest.MatcherAssert
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.Optional
import org.hamcrest.Matchers

@DatabaseSetup(LinkRepositoryTest.DATASET)
@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = [LinkRepositoryTest.DATASET])
open class LinkRepositoryTest : AbstractRepositoryTest() {

    @Autowired
    lateinit var repository: LinkRepository

    companion object {
        const val DATASET = "classpath:datasets/link-table.xml"
        private val LINK_1_TEXT: String = "http://www.eveonline.com"
        private val LINK_TBS_TEXT: String = "http://www.ru"
        private val LINK_1_ID: Long = 100500L
        private val LINK_NOT_FOUND: Long = 1L
    }

    @Test
    fun findOneExisting() {
        val got: Optional<Link> = repository.findById(LINK_1_ID)
        MatcherAssert.assertThat(got.isPresent, Matchers.equalTo(true))
        val link = got.get()
        MatcherAssert.assertThat(link, Matchers.equalTo(Link(LINK_1_TEXT, LINK_1_ID)))
    }

    @Test
    fun findOneNotExisting() {
        val got: Optional<Link> = repository.findById(LINK_NOT_FOUND)
        MatcherAssert.assertThat(got.isPresent, Matchers.equalTo(false))
    }

    @Test
    fun saveNew() {
        val toBeSaved: Link = Link(LINK_TBS_TEXT)
        val got: Link = repository.save(toBeSaved)
        val list: List<Link> = repository.findAll()
        MatcherAssert.assertThat(list, Matchers.hasSize(4))
        MatcherAssert.assertThat(got.text, Matchers.equalTo(LINK_TBS_TEXT))
    }
}