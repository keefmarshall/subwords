package uk.eleusis.words

import org.apache.commons.collections4.Bag
import org.apache.commons.collections4.SetValuedMap
import org.apache.commons.collections4.multimap.HashSetValuedHashMap

class Anagrammer {
    private val anagramMap: SetValuedMap<Bag<Char>, String> = HashSetValuedHashMap<Bag<Char>, String>()

    fun buildMap(words: Collection<String>) {
        anagramMap.clear()
        words.forEach {
            anagramMap.put(it.toSortedBag(), it)
        }
    }

    fun findAllSubWords(word: String): Set<String> {
        val bag = word.toSortedBag()
        return anagramMap.keys()
                .filter {
                    bag.containsAll(it)
                }
                .flatMap {
                    anagramMap[it]
                }.toSortedSet()
    }

}