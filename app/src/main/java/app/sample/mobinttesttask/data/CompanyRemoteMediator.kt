package app.sample.mobinttesttask.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import app.sample.mobinttesttask.core.utils.OffsetSaver
import app.sample.mobinttesttask.data.local.CompanyDatabase
import app.sample.mobinttesttask.data.local.model.CompanyEntity
import app.sample.mobinttesttask.data.mappers.toCompanyEntity
import app.sample.mobinttesttask.data.network.NetworkCompanyClient
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class CompanyRemoteMediator @Inject constructor(
    private val companyDb: CompanyDatabase,
    private val networkCompanyClient: NetworkCompanyClient
) : RemoteMediator<Int, CompanyEntity>(){

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CompanyEntity>
    ): MediatorResult {

        return try {

            val offset: Int = when(loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()

                    if (lastItem == null) {
                        0
                    } else {
                        OffsetSaver.getOffset() + state.config.pageSize
                    }
                }
            }

            val listOfCompanyDto = networkCompanyClient.getCompanies(
                offset = offset,
                limit = state.config.pageSize
            )

            companyDb.withTransaction {

                if (loadType == LoadType.REFRESH) {
                    companyDb.companyDao().clearAll()
                }

                val listOfCompanyEntity = listOfCompanyDto.map { companyDto -> companyDto.toCompanyEntity() }
                companyDb.companyDao().insertAll(listOfCompanyEntity)
            }

            MediatorResult.Success(
                endOfPaginationReached = listOfCompanyDto.isEmpty()
            )

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }


}