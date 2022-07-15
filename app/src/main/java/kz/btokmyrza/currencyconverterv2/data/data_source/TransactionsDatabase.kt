package kz.btokmyrza.currencyconverterv2.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kz.btokmyrza.currencyconverterv2.domain.enitities.Transaction

@Database(
    entities = [Transaction::class],
    version = 2
)
@TypeConverters(Converters::class)
abstract class TransactionsDatabase : RoomDatabase() {

    abstract val transactionDao: TransactionDao

    companion object {
        const val DATABASE_NAME = "transactions_db"
    }

}