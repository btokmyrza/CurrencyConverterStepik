package kz.btokmyrza.currencyconverterv2.domain.repository

import androidx.lifecycle.LiveData
import kz.btokmyrza.currencyconverterv2.domain.enitities.Transaction

interface TransactionsRepository {

    suspend fun upsert(transaction: Transaction): Long

    suspend fun getAllTransactions(): List<Transaction>

    suspend fun deleteTransaction(transaction: Transaction)

}