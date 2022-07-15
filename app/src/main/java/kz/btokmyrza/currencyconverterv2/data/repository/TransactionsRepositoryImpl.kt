package kz.btokmyrza.currencyconverterv2.data.repository

import androidx.lifecycle.LiveData
import kz.btokmyrza.currencyconverterv2.data.data_source.TransactionDao
import kz.btokmyrza.currencyconverterv2.domain.enitities.Transaction
import kz.btokmyrza.currencyconverterv2.domain.repository.TransactionsRepository

class TransactionsRepositoryImpl(
    private val dao: TransactionDao
) : TransactionsRepository {

    override suspend fun upsert(transaction: Transaction): Long {
        return dao.upsert(transaction)
    }

    override suspend fun getAllTransactions(): List<Transaction> {
        return dao.getAllTransactions()
    }

    override suspend fun deleteTransaction(transaction: Transaction) {
        dao.deleteTransaction(transaction)
    }

}