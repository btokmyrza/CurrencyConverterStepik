package kz.btokmyrza.currencyconverterv2.data.data_source

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class MigrationFromVersion1ToVersion2 : Migration(1, 2){
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("DROP TABLE transactions")
        database.execSQL("CREATE TABLE IF NOT EXISTS `transactions` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, `amount` INTEGER NOT NULL, `currency` TEXT)")
    }
}
