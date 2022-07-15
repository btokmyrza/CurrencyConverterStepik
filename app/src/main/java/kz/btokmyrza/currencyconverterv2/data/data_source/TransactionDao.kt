package kz.btokmyrza.currencyconverterv2.data.data_source

import androidx.room.*
import kz.btokmyrza.currencyconverterv2.domain.enitities.Transaction

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(transaction: Transaction): Long

    @Query("SELECT * FROM transactions")
    suspend fun getAllTransactions(): List<Transaction>

    @Delete
    suspend fun deleteTransaction(transaction: Transaction)

}