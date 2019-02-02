package uk.eleusis.words

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WordsApplication

fun main(args: Array<String>) {
    runApplication<WordsApplication>(*args)
}

