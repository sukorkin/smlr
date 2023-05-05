package com.example.smlr.controllers

import com.example.smlr.SmlrApplication
import com.example.smlr.service.KeyMapperService
import org.junit.Before
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.context.WebApplicationContext
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
//import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MockMvcBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@RunWith(SpringJUnit4ClassRunner::class)
//@SpringApplicationConfiguration
@SpringBootTest(classes = [SmlrApplication::class])
@WebAppConfiguration
class RedirectControllerTest {

    private val HEADER_VALUE = "http://www.eveonline.com"
    private val HEADER_NAME = "Location"
    private val REDIRECT_STATUS: Int = 302
    private val NOT_FOUND: Int = 404
    private val PATH = "aAbBcCdD"
    private val BAD_PATH = "olololo"

    @Autowired
    lateinit var webApplicationContext: WebApplicationContext

    lateinit var mockMvc: MockMvc

    @Mock
    lateinit var sevice: KeyMapperService

    @Autowired
    @InjectMocks
    lateinit var controller: RedirectController

    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
        Mockito.`when`(sevice.getLink(PATH)).thenReturn(KeyMapperService.Get.Link(HEADER_VALUE))
        Mockito.`when`(sevice.getLink(BAD_PATH)).thenReturn(KeyMapperService.Get.NotFound(BAD_PATH))
    }

    @Test
    fun controllerMustRedirectUsWhenRequestIsSuccessful() {
        mockMvc.perform(get("/$PATH"))
            .andExpect(status().`is`(REDIRECT_STATUS))
            .andExpect(header().string(HEADER_NAME, HEADER_VALUE))
    }

    @Test
    fun controllerMustReturn404IfBadKey() {
        mockMvc.perform(get("/$BAD_PATH"))
            .andExpect(status().`is`(NOT_FOUND))
    }

}