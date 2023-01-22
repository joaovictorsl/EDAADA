package edu.joaovsl.edaAda.core

import java.util.*

data class Transaction(
    val agency: String,
    val account: String,
    val bank: String,
    val value: Double,
    val transactionType: TransactionType,
    val time: Calendar
) : Comparable<Transaction> {
    override fun compareTo(other: Transaction): Int {
        if (time != other.time)
            return time.compareTo(other.time)
        else if (transactionType != other.transactionType)
            return transactionType.compareTo(other.transactionType)
        else if (value != other.value)
            return (value - other.value).toInt()

        return 0
    }

    override fun toString(): String {
        return "Agência: $agency | Conta: $account | Banco: $bank | Valor: $value | Tipo de transação: $transactionType | Horário: ${time.get(Calendar.HOUR_OF_DAY)}:${time.get(Calendar.MINUTE)}:${time.get(Calendar.SECOND)} - ${time.get(Calendar.DAY_OF_MONTH)}/${time.get(Calendar.MONTH)}/${time.get(Calendar.YEAR)}"
    }
}
