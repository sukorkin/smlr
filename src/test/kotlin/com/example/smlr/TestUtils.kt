package com.example.smlr

import org.mockito.Mockito

fun <T> whenever(call: T) =  Mockito.`when`(call)