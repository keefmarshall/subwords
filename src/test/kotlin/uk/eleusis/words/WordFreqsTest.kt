package uk.eleusis.words

import org.apache.commons.collections4.bag.TreeBag
import org.junit.Assert.*
import org.junit.Test

class WordFreqsTest {
    val WORD_FREQ_FILE = "src/main/resources/written.num.o5"

    @Test
    fun loadFile() {
        val wordFreqs = WordFreqs()
        val list = wordFreqs.loadFile(WORD_FREQ_FILE)
        println("Total filtered word list size: ${list.size}")

        val distBag = wordFreqs.lengthDistribution(list)
        distBag.uniqueSet().forEach {
            println("${it} ${distBag.getCount(it)}")
        }
    }

    @Test
    fun nineLetterWords() {
        val wordFreqs = WordFreqs()
        val list = wordFreqs.loadFile(WORD_FREQ_FILE)
        val nineLetterWordFs = list.filter { it.word.length == 9 }
        println("Nine letter WordF instances: ${nineLetterWordFs.count()}")
        val nineLetterWordBag = TreeBag<String>()
        nineLetterWordFs.map { nineLetterWordBag.add(it.word) }
        println("Nine letter unique words: ${nineLetterWordBag.uniqueSet().size}")
        nineLetterWordBag.uniqueSet().drop(4000).take(50).map(::println)
    }

    @Test
    fun wordTypes() {
        val wordFreqs = WordFreqs()
        val list = wordFreqs.loadFile(WORD_FREQ_FILE)
        list.filter { it.word == "jordanian" }.map(::println)
    }
}