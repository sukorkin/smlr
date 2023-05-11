package com.example.smlr.controllers

import com.example.smlr.SmlrApplication
import com.example.smlr.service.KeyMapperService
import com.example.smlr.whenever
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.context.WebApplicationContext
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@TestPropertySource(locations = ["classpath:repositories-test.properties"])
@RunWith(SpringJUnit4ClassRunner::class)
//@SpringApplicationConfiguration
@SpringBootTest(classes = [SmlrApplication::class])
@WebAppConfiguration
class AddControllerTest {

    //    private val HEADER_VALUE = "http://www.eveonline.com"
//    private val HEADER_NAME = "Location"
//    private val REDIRECT_STATUS: Int = 302
    private val PATH = "add"
    private val LINK = "link"
    private val KEY = "key"

    @Autowired
    lateinit var webApplicationContext: WebApplicationContext

    lateinit var mockMvc: MockMvc

    @Mock
    lateinit var sevice: KeyMapperService

    @Autowired
    @InjectMocks
    lateinit var controller: AddController

    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)
        mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .build()
        whenever(sevice.add(LINK)).thenReturn(KEY)
    }

    @Test
    fun whenUserAddLinkHeTakesKey() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/$PATH")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonObjectMapper().writeValueAsString(AddController.AddRequest(LINK)))
        )
            .andExpect(MockMvcResultMatchers.jsonPath("$.key", Matchers.equalTo(KEY)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.link", Matchers.equalTo(LINK)))
    }

    @Test
    fun whenUserAddLinkByFormHeTakesAWebPage() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/addhtml")
                .param("link", LINK)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(KEY)))
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(LINK)))
    }
}