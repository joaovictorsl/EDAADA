package edu.joaovsl.edaAda.reader

import edu.joaovsl.edaAda.processLine
import java.io.FileReader

class CSVReader(
    private val filename: String,
    private val skipHeader: Boolean
) {
    val lines: List<String>
    get() {
        val fr = FileReader(filename)
        val lines = mutableListOf<String>()

        fr.use {
            val content = it.readText().split('\n')
            val iter = content.iterator()

            if (skipHeader)
                iter.next()

            while(iter.hasNext())
                lines.add(iter.next())
        }

        return lines
    }
}
