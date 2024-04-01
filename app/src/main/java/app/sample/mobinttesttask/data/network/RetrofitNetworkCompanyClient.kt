package app.sample.mobinttesttask.data.network

import android.util.Log
import app.sample.mobinttesttask.core.utils.OffsetSaver
import app.sample.mobinttesttask.data.network.model.CompanyDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class RetrofitNetworkCompanyClient @Inject constructor(
    private val companyApi: ApiService
) : NetworkCompanyClient {
    override suspend fun getCompanies(offset: Int, limit: Int): List<CompanyDto> {
        return suspendCoroutine { continuation ->
            val call = companyApi.getCompaniesFromRemoteDataServer(
                requestBody = mapOf("offset" to offset, "limit" to limit)
            )

            call.enqueue(object : retrofit2.Callback<CompanyDataResponse> {
                override fun onResponse(
                    call: Call<CompanyDataResponse>,
                    response: Response<CompanyDataResponse>
                ) {
                    val responseFromServer = response.body()
                    if (response.isSuccessful && responseFromServer != null) {
                        OffsetSaver.setOffset(responseFromServer.offset)
                        continuation.resume(responseFromServer.companies)
                    } else {
                        continuation.resume(emptyList())
                    }
                }

                override fun onFailure(call: Call<CompanyDataResponse>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }
}