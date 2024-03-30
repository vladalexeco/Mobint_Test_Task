package app.sample.mobinttesttask.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import app.sample.mobinttesttask.data.local.CompanyDatabase
import app.sample.mobinttesttask.data.mappers.toCompanyEntity
import app.sample.mobinttesttask.data.network.ApiService
import app.sample.mobinttesttask.data.network.CompanyDataResponse
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class CompanyRemoteMediator(
    private val companyDb: CompanyDatabase,
    private val companyApi: ApiService
) : RemoteMediator<Int, CompanyDataResponse>(){

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CompanyDataResponse>
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
                        lastItem.offset + state.config.pageSize
                    }
                }
            }

            val requestBody = mapOf(
                "offset" to offset,
                "limit" to state.config.pageSize
            )

            val companyDataResponse = companyApi.getCompaniesFromRemoteDataServer(
                requestBody = requestBody
            )

            val listOfCompanyDto = companyDataResponse.companies

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