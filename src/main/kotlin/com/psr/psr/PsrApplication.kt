package com.psr.psr

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class PsrApplication

fun main(args: Array<String>) {
	runApplication<PsrApplication>(*args)
}
