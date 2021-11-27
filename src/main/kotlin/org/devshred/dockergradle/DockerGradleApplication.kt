package org.devshred.dockergradle

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
@RestController
class DockerGradleApplication {
    @GetMapping
    fun hello() = "hello"
}

fun main(args: Array<String>) {
    runApplication<DockerGradleApplication>(*args)
}
