package com.example.demo

import com.example.demo.model.Test

class UsesTest {
    fun doTheThing() : Test {
        val thing = Test.newBuilder()
            .setValue("hello")
            .build()

        System.out.println("Thing's value: ${thing.value}")
        return thing
    }
}