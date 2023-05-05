package com.example.smlr.service

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DefaultKeyMapperServiceTest {



    @InjectMocks
    val sevice: KeyMapperService = DefaultKeyMapperService()

    private val KEY = "aAbBcCdD"
    private val LINK_A = "http://www.google.com"
    private val LINK_B = "http://www.yahoo.com"
    private val KEY_A = "abc"
    private val KEY_B = "cde"
    private val ID_A = 10000000L
    private val ID_B = 10000001L


    @Mock
    lateinit var converter: KeyConverterService

    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)
        Mockito.`when`(converter.keyToId(KEY_A)).thenReturn(ID_A)
        Mockito.`when`(converter.idToKey(ID_A)).thenReturn(KEY_A)
        Mockito.`when`(converter.keyToId(KEY_B)).thenReturn(ID_B)
        Mockito.`when`(converter.idToKey(ID_B)).thenReturn(KEY_B)
    }
//    private val LINK = "http://www.eveonline.com"
//    private val LINK_NEW = "http://wow.ru"

//    @Test
//    fun clientCanAddNewKeyWithLink() {
//        Assert.assertEquals(KeyMapperService.Add.Success(KEY, LINK), sevice.add(KEY, LINK))
//        Assert.assertEquals(KeyMapperService.Get.Link(LINK), sevice.getLink(KEY))
//    }
//
//    @Test
//    fun clientCanNotAddExistingKey() {
//        sevice.add(KEY, LINK)
//        Assert.assertEquals(KeyMapperService.Add.AlreadyExist(KEY), sevice.add(KEY, LINK_NEW))
//        Assert.assertEquals(KeyMapperService.Get.Link(LINK), sevice.getLink(KEY))
//    }

    @Test
    fun clientCanAddLinks() {
        val keyA = sevice.add(LINK_A)
        Assert.assertEquals(KeyMapperService.Get.Link(LINK_A), sevice.getLink(keyA))
        val keyB = sevice.add(LINK_B)
        Assert.assertEquals(KeyMapperService.Get.Link(LINK_B), sevice.getLink(keyB))
        Assert.assertNotEquals(keyA, keyB)
        println()
    }

    @Test
    fun clientCanNotTakeLinkIfKeyNotFoundInService() {
        Assert.assertEquals(KeyMapperService.Get.NotFound(KEY), sevice.getLink(KEY))
    }
}