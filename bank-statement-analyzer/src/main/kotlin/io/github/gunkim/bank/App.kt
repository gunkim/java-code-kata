package io.github.gunkim.bank

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.abs

fun main() {
    val content = readFile("bank-transaction.csv")
    val lines = content.split("\n").dropLast(1)
    val transactions = parse(lines)

    println("==== Transactions ====")
    transactions.forEach {
        println(it)
    }
    println("======================")

    println("The total for all transactions is ${Transactions(transactions).totalAmount}")
}

private fun readFile(fileName: String): String {
    val inputStream = ClassLoader.getSystemClassLoader()
        .getResourceAsStream(fileName)
        ?: throw IllegalArgumentException("file not found! $fileName")

    inputStream.bufferedReader().use {
        return it.readText()
    }
}

private fun parse(lines: List<String>): List<Transaction> {
    return lines.map { line ->
        val columns = line.split(",")

        val amount = columns[1].toInt()
        Transaction(
            name = columns[2],
            date = LocalDate.parse(columns[0], DateTimeFormatter.ofPattern("dd-MM-yyyy")),
            type = amount.takeIf { it > 0 }?.let { Transaction.Type.DEPOSIT }
                ?: Transaction.Type.WITHDRAWAL,
            amount = abs(amount)
        )
    }
}
