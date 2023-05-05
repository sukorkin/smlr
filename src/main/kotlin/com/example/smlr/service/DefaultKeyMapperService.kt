package com.example.smlr.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

@Service
class DefaultKeyMapperService: KeyMapperService {

    @Autowired
    lateinit var converter: KeyConverterService
    private val map: MutableMap<Long, String> = ConcurrentHashMap()

    val sequence = AtomicLong(10000000L)

    override fun add(link: String): String {
        val id = sequence.getAndIncrement()
        val key = converter.idToKey(id)
        map[id] = link
        return key

//        return if(map.contains(key)) {
//            KeyMapperService.Add.AlreadyExist(key)
//        } else {
//            map[key] = link
//            KeyMapperService.Add.Success(key, link)
//        }
    }

    override fun getLink(key: String): KeyMapperService.Get {
        val id = converter.keyToId(key)
        val result = map[id]
        if(result == null) {
            return KeyMapperService.Get.NotFound(key)
        } else {
            return KeyMapperService.Get.Link(result)
        }
    }
}