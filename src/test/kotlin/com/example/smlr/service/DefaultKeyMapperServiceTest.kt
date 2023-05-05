package com.example.smlr.service

import org.junit.Assert
import org.junit.Test

class DefaultKeyMapperServiceTest {

    val sevice: KeyMapperService = DefaultKeyMapperService()

    private val KEY = "aAbBcCdD"
    private val LINK = "http://www.eveonline.com"
    private val LINK_NEW = "http://wow.ru"

    @Test
    fun clientCanAddNewKeyWithLink() {
        Assert.assertEquals(KeyMapperService.Add.Success(KEY, LINK), sevice.add(KEY, LINK))
        Assert.assertEquals(KeyMapperService.Get.Link(LINK), sevice.getLink(KEY))
    }

    @Test
    fun clientCanNotAddExistingKey() {
        sevice.add(KEY, LINK)
        Assert.assertEquals(KeyMapperService.Add.AlreadyExist(KEY), sevice.add(KEY, LINK_NEW))
        Assert.assertEquals(KeyMapperService.Get.Link(LINK), sevice.getLink(KEY))
    }

    @Test
    fun clientCanNotTakeLinkIfKeyNotFoundInService() {
        Assert.assertEquals(KeyMapperService.Get.NotFound(KEY), sevice.getLink(KEY))
    }
}