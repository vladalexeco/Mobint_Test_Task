package app.sample.mobinttesttask.core.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import app.sample.mobinttesttask.data.CompanyRemoteMediator
import app.sample.mobinttesttask.data.local.CompanyDatabase
import app.sample.mobinttesttask.data.local.model.CompanyEntity
import app.sample.mobinttesttask.data.network.ApiService
import app.sample.mobinttesttask.data.network.CompanyDataResponse
import app.sample.mobinttesttask.data.network.NetworkCompanyClient
import app.sample.mobinttesttask.data.network.RetrofitNetworkCompanyClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCompanyDataBase(@ApplicationContext context: Context): CompanyDatabase {
        return Room.databaseBuilder(
            context,
            CompanyDatabase::class.java,
            "companies.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCompanyApi(): ApiService {
        return Retrofit.Builder()
            .baseUrl(ApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideNetworkCompanyClient(apiService: ApiService): NetworkCompanyClient {
        return RetrofitNetworkCompanyClient(apiService)
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideCompanyPager(
        companyDb: CompanyDatabase,
        networkCompanyClient: NetworkCompanyClient
    ): Pager<Int, CompanyEntity> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = CompanyRemoteMediator(
                companyDb = companyDb,
                networkCompanyClient = networkCompanyClient
            ),
            pagingSourceFactory = {
                companyDb.companyDao().pagingSource()
            }
        )
    }
}