package uk.eleusis.words

import java.io.File

object WordFile {
    fun loadFile(path: String): List<String> = File(path).readLines()
}
