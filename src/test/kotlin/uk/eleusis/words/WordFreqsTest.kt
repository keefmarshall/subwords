package uk.eleusis.words

import org.apache.commons.collections4.bag.TreeBag
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class WordFreqsTest {
    val WORD_FREQ_FILE = "src/main/resources/written.num.o5"
    val UK_WORDS_FILE = "src/main/resources/UKACD18plus.txt"

    @Test
    fun loadFile() {
        val wordFreqs = WordFreqs()
        val list = wordFreqs.loadFile(WORD_FREQ_FILE, UK_WORDS_FILE)
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
    fun fiveLetterWords() {
        nLetterWords(5, 1900, 100)
    }

    @Test
    fun nineLetterWords() {
        nLetterWords(9)
    }

    private fun nLetterWords(n: Int, dropped: Int = 500, taken: Int = 50) {
        val wordFreqs = WordFreqs()
        val list = wordFreqs.loadFile(WORD_FREQ_FILE, UK_WORDS_FILE)
        val nLetterWordFs = list.filter { it.word.length == n }
        println("$n letter WordF instances: ${nLetterWordFs.count()}")
        val nLetterWordBag = TreeBag<String>()
        nLetterWordFs.map { nLetterWordBag.add(it.word) }
        println("$n letter unique words: ${nLetterWordBag.uniqueSet().size}")
        nLetterWordBag.uniqueSet().drop(dropped).take(taken).map(::println)

    }

    @Test
    fun wordTypes() {
        val wordFreqs = WordFreqs()
        val list = wordFreqs.loadFile(WORD_FREQ_FILE, UK_WORDS_FILE)
        list.filter { it.word == "mdc" }.map(::println)
        list.filter { it.word == "doug" }.map(::println)
        println("${wordFreqs.wordIndex["jones"]}")
        println("${wordFreqs.wordIndex["denominators"]}")
    }

    @Test
    fun scoreWords() {
        val wordFreqs = WordFreqs()
        wordFreqs.loadFile(WORD_FREQ_FILE, UK_WORDS_FILE)
        val scoredWords = wordFreqs.scoreWords()
        assertEquals(56486, scoredWords.size)
        scoredWords.drop(500).take(100).forEach(::println)
        println("Min score: ${scoredWords.minBy { it.second?.toDouble() ?: 0.0 }}")
        println("Max score: ${scoredWords.maxBy { it.second?.toDouble() ?: 0.0 }}")
    }
}

