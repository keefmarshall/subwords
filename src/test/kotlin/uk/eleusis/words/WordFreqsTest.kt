package uk.eleusis.words

import org.apache.commons.collections4.bag.TreeBag
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
    fun threeLetterWords() {
        nLetterWords(3)
    }

    @Test
    fun fourLetterWords() {
        nLetterWords(4)
    }

    @Test
    fun nineLetterWords() {
        nLetterWords(9)
    }

    private fun nLetterWords(n: Int) {
        val wordFreqs = WordFreqs()
        val list = wordFreqs.loadFile(WORD_FREQ_FILE)
        val nLetterWordFs = list.filter { it.word.length == n }
        println("$n letter WordF instances: ${nLetterWordFs.count()}")
        val nLetterWordBag = TreeBag<String>()
        nLetterWordFs.map { nLetterWordBag.add(it.word) }
        println("$n letter unique words: ${nLetterWordBag.uniqueSet().size}")
        nLetterWordBag.uniqueSet().drop(500).take(50).map(::println)

    }

    @Test
    fun wordTypes() {
        val wordFreqs = WordFreqs()
        val list = wordFreqs.loadFile(WORD_FREQ_FILE)
        list.filter { it.word == "mdc" }.map(::println)
        list.filter { it.word == "doug" }.map(::println)
    }
}

