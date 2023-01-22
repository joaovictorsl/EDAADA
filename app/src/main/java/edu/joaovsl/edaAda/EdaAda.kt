package edu.joaovsl.edaAda

import android.os.Build
import androidx.annotation.RequiresApi
import edu.joaovsl.edaAda.core.Transaction
import edu.joaovsl.edaAda.core.TransactionType
import edu.joaovsl.edaAda.reader.CSVReader
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.util.*

private val holderMap: MutableMap<String, MutableSet<Transaction>> = mutableMapOf()
private const val AGENCY = 0
private const val ACCOUNT = 1
private const val BANK = 2
private const val HOLDER = 3
private const val TRANSACTION_TYPE = 4
private const val DATETIME = 5
private const val VALUE = 6

@RequiresApi(Build.VERSION_CODES.O)
fun main() {
    val lines = CSVReader("${System.getProperty("user.dir")}/app/src/main/java/edu/joaovsl/edaAda/test.csv", true).lines

    lines.forEach { line: String ->
        processLine(line)
    }

    holderMap.forEach { entry ->
        val sum = entry.value.sumOf {
            if (it.transactionType == TransactionType.DEPOSIT) it.value else -it.value
        }

        println("${entry.key}: R$ $sum")
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun processLine(line: String) {
    val splitLine = line.split(',')
    val holderName = splitLine[HOLDER]

    if (!holderMap.containsKey(holderName))
        holderMap[holderName] = TreeSet()

    holderMap[holderName]!!.add(
        Transaction(
            splitLine[AGENCY],
            splitLine[ACCOUNT],
            splitLine[BANK],
            splitLine[VALUE].toDouble(),
            splitLine[TRANSACTION_TYPE].toTransactionType(),
            splitLine[DATETIME].toCalendar()
        )
    )
}

private fun String.toTransactionType(): TransactionType {
    return when(this.uppercase()) {
        "DEPOSITO" -> TransactionType.DEPOSIT
        "SAQUE" -> TransactionType.WITHDRAW
        else -> throw TypeCastException("This String ($this) cannot be casted to a TransactionType.")
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun String.toCalendar(): Calendar {
    try {
        val date = this.split('T')[0].split('-').map { it.toInt() }
        val dateTime = this.split('T')[1].split(':').map { it.toInt() }
        return Calendar.Builder().setDate(date[0], date[1], date[2]).setTimeOfDay(dateTime[0], dateTime[1], dateTime[2]).build()
    } catch (e: Exception) {
        throw IllegalArgumentException("This String ($this) cannot be casted to a Calendar")
    }
}
