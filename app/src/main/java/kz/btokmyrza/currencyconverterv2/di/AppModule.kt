package kz.btokmyrza.currencyconverterv2.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kz.btokmyrza.currencyconverterv2.data.data_source.MigrationFromVersion1ToVersion2
import kz.btokmyrza.currencyconverterv2.data.data_source.TransactionsDatabase
import kz.btokmyrza.currencyconverterv2.data.repository.TransactionsRepositoryImpl
import kz.btokmyrza.currencyconverterv2.domain.repository.TransactionsRepository
import kz.btokmyrza.currencyconverterv2.presentation.adapters.ConvertCurrencyAdapter
import kz.btokmyrza.currencyconverterv2.presentation.adapters.CountryFlagsAdapter
import kz.btokmyrza.currencyconverterv2.util.DispatcherProvider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTransactionsDatabase(app: Application): TransactionsDatabase {
        return Room.databaseBuilder(
            app,
            TransactionsDatabase::class.java,
            TransactionsDatabase.DATABASE_NAME
        )
            .addMigrations(
                MigrationFromVersion1ToVersion2()
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideTransactionsRepository(db: TransactionsDatabase): TransactionsRepository {
        return TransactionsRepositoryImpl(db.transactionDao)
    }

    @Provides
    @Singleton
    fun provideConvertCurrencyAdapter(
        @ApplicationContext context: Context
    ): ConvertCurrencyAdapter = ConvertCurrencyAdapter(context)

    @Provides
    @Singleton
    fun provideCountryFlagsAdapter(
        @ApplicationContext context: Context
    ): CountryFlagsAdapter = CountryFlagsAdapter(context)

    @Provides
    @Singleton
    fun provideDispatchers(): DispatcherProvider = object : DispatcherProvider {
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined
    }

}