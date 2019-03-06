package uk.eleusis.words

import org.junit.BeforeClass
import org.junit.Test

class AnagrammerTest {

    companion object {
        lateinit var anag: Anagrammer
        lateinit var scoreWords: Map<String, Int>

        @JvmStatic
        @BeforeClass
        fun setup() {
            val wordFreqs = WordFreqs()
            wordFreqs.loadFile(WordFreqsTest.WORD_FREQ_FILE, WordFreqsTest.UK_WORDS_FILE)
            val wordList = wordFreqs.wordIndex.keys().uniqueSet()
            anag = Anagrammer()
            anag.buildMap(wordList)

            scoreWords = wordFreqs.scoreWords().toMap()
        }
    }

    @Test
    fun findAllSubWords() {
        val stationWords = countSubwords("station")
//        stationWords.forEach { println("$it: ${scoreWords[it]}") }

        val trainWords = countSubwords("railway")

        val intersect = trainWords.intersect(stationWords)
        println("Intersect: ${intersect.size} words")
    }

    private fun countSubwords(word: String): Set<String> {
        val subWords = anag.findAllSubWords(word)
        println("Found ${subWords.size} in '$word'.")
        return subWords
    }
}