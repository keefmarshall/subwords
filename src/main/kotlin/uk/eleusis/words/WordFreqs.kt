package uk.eleusis.words

import org.apache.commons.collections4.Bag
import org.apache.commons.collections4.bag.TreeBag
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap
import java.io.File
import java.util.*

// http://www.kilgarriff.co.uk/bnc-readme.html

class WordFreqs {
    val wordIndex = ArrayListValuedHashMap<String, WordF>()

    fun loadFile(path: String): List<WordF> {
        val file = File(path)
        wordIndex.clear()
        return file.useLines { lines ->
            lines.map(::parseFreqLine)
                    .filter { it.corpusFreq > 10 } // appears in more than ten documents
                    .filter { it.word.length > 2 }
                    .filter { isAllLetters(it.word) } // NB the Kilgarriff files have e.g. &eacute; symbols in there
                    .filter { !it.category.contains("np0", true) } // no proper nouns
                    .map {
                        // add to index:
                        wordIndex.put(it.word, it)
                        it // return identity
                    }
                    .toList() // Returning sequence doesn't work as file is closed when we exit this block
        }
    }

    private fun parseFreqLine(line: String): WordF {
        val entries = line.split(" ")
        return WordF(entries[0].toInt(), entries[1], entries[2], entries[3].toInt())
    }

    fun lengthDistribution(words: List<WordF>): Bag<Int> {
        val distBag = TreeBag<Int>()
        words.forEach { distBag.add(it.word.length) }
        return distBag
    }

}

data class WordF(val freq: Int, val word: String, val category: String, val corpusFreq: Int)

fun isAllLetters(str: String): Boolean {
    str.forEach {
        if (!it.isLetter()) {
            return false
        }
    }

    return true
}