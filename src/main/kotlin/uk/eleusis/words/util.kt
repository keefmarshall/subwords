package uk.eleusis.words

import org.apache.commons.collections4.Bag
import org.apache.commons.collections4.SortedBag
import org.apache.commons.collections4.bag.HashBag
import org.apache.commons.collections4.bag.TreeBag

fun String.toBag(): Bag<Char> = HashBag(this.toList())
fun String.toSortedBag(): SortedBag<Char> = TreeBag(this.toList())