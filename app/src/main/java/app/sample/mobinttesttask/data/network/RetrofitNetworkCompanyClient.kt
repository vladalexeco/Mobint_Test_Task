package app.sample.mobinttesttask.data.network

import app.sample.mobinttesttask.core.utils.OffsetSaver
import app.sample.mobinttesttask.data.network.model.CompanyDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RetrofitNetworkCompanyClient @Inject constructor(
    private val companyApi: ApiService
) : NetworkCompanyClient {
    override suspend fun getCompanies(offset: Int, limit: Int): List<CompanyDto> {
        return withContext(Dispatchers.IO) {

            val requestBody = mapOf(
                "offset" to offset,
                "limit" to limit
            )

            val companyDataResponse = companyApi.getCompaniesFromRemoteDataServer(
                requestBody = requestBody
            )

            OffsetSaver.setOffset(companyDataResponse.offset)
            OffsetSaver.setLimit(companyDataResponse.limit)

            companyDataResponse.companies

        }
    }

}