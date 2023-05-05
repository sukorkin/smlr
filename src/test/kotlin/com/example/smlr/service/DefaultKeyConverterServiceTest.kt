package com.example.smlr.service

import org.junit.Test
import java.util.*
import org.junit.Assert

class DefaultKeyConverterServiceTest {

    val service: KeyConverterService = DefaultKeyConverterService()
    @Test
    fun givenIdMustBeConvertibleBothWays() {
        val rand = Random()
        for (i in 0..1000L) {
            val initialId = Math.abs(rand.nextLong())
            val key = service.idToKey(initialId)
            val id = service.keyToId(key)
            Assert.assertEquals(initialId, id)
        }
    }
}