package kz.btokmyrza.currencyconverterv2.domain.enitities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kz.btokmyrza.currencyconverterv2.presentation.models.Currency

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val amount: Int,
    val currency: Currency?
)
