package io.github.gunkim.bank

import java.time.LocalDate

data class Transaction(
    val name: String,
    val date: LocalDate,
    val type: Type,
    val amount: Int,
) {
    enum class Type {
        DEPOSIT,
        WITHDRAWAL;
    }
}
