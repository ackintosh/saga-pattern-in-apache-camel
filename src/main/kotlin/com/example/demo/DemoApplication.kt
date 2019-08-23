package com.example.demo

import org.apache.camel.CamelContext
import org.apache.camel.Exchange
import org.apache.camel.Processor
import org.apache.camel.impl.saga.InMemorySagaService
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ImportResource
import org.springframework.stereotype.Component

@SpringBootApplication
@ImportResource("classpath:my-camel.xml")
class DemoApplication

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}

@Component
class DemoBean {
    @Bean
    fun addService(camelContext: CamelContext) : InMemorySagaService {
        // see https://camel.apache.org/manual/latest/saga-eip.html#_using_the_in_memory_saga_service
        val service = InMemorySagaService()
        camelContext.addService(service)

        return service
    }
}

@Component
class ThrowException : Processor {
    override fun process(exchange: Exchange) {
        throw RuntimeException("something wrong...")
    }
}