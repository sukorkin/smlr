package com.example.smlr.service

import org.springframework.stereotype.Service

@Service
class DefaultKeyConverterService: KeyConverterService {

    private final val chars = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890-_".toCharArray()
    val charToLong = (chars.indices)
        .map { i ->  Pair(chars[i], i.toLong())}
        .toMap()
    override fun idToKey(id: Long): String {
        var n = id
        val builder = StringBuilder()
        while (n != 0L) {
            builder.append(chars[(n % chars.size).toInt()])
            n /= chars.size
        }
        return builder.reverse().toString()
    }

    override fun keyToId(key: String) = key
        .map { c -> charToLong[c]!! }
        .fold(0L) { a, b -> a * chars.size + b }
}