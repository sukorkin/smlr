package com.example.smlr.controllers

import com.example.smlr.service.KeyMapperService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class AddController {

    @Value("\${smlr.prefix}")
    private lateinit var prefix: String
    @Autowired
    lateinit var service: KeyMapperService

    @RequestMapping(path = ["add"], method = [RequestMethod.POST])
    @ResponseBody
    fun add(@RequestBody request: AddRequest) = ResponseEntity.ok(AddResponse(request.link, service.add(request.link)))

    @RequestMapping(path = ["addhtml"], method = [RequestMethod.POST])
    fun addHtml(model: Model, @RequestParam(value = "link", required = true) link: String): String {
        val result = add(link)
        model.addAttribute("link", result.link)
        model.addAttribute("keyed", prefix + result.key)
        return "result"
    }

    data class AddRequest(val link: String)
    data class AddResponse(val link: String, val key: String)

    private fun add(link: String) = AddResponse(link, service.add(link))
}