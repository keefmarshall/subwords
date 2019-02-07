package uk.eleusis.words

import org.apache.commons.collections4.Bag
import org.apache.commons.collections4.bag.TreeBag
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap
import java.io.File
import java.util.*

// http://www.kilgarriff.co.uk/bnc-readme.html

class WordFreqs {
    val wordIndex = ArrayListValuedHashMap<String, WordF>()
    val scoredWords = TreeMap<String, Int>()

    fun loadFile(freqPath: String, ukWordsPath: String): List<WordF> {
        var ukWordSet = WordFile.loadFile(ukWordsPath).toSet()
        wordIndex.clear()
        val freqFile = File(freqPath)
        return freqFile.useLines { lines ->
            lines.map(::parseFreqLine)
                    .filter { ukWordSet.contains(it.word) } // match against UKACD18
//                    .filter { it.corpusFreq >= 10 } // appears in at least ten documents
                    .filter { it.word.length > 2 } // three letters and above
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

    fun scoreWords(): List<Pair<String, Int>> =
        wordIndex.asMap().map {
            val word = it.key
            val score = it.value
                    .map { wf ->
                       Math.log(wf.freq.toDouble()) +
                        (3 * Math.log(wf.corpusFreq.toDouble())) -
                        (2.5 * (word.length - 3))
                    }
                    .map { score -> Math.round(Math.pow(28 - score, 1.75) / 50.0) + 1}
                    .min()
            (word to (score?.toInt() ?: 0))
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