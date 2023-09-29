package com.nextgen.beritaku.core.utils

import com.nextgen.beritaku.core.utils.Constants.WORD_PER_MINUTE

object Utils {

    /**
     * this method use to generate estimate to read the news based on the word length
     */
    fun calculateReadTime(text: String): String {
        val clearText = text.split(" ")
        val wordCount = clearText.size
        val estimate = wordCount / WORD_PER_MINUTE
        return "$estimate min"
    }

}