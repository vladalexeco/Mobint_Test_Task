package app.sample.mobinttesttask.data.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("mobileapp/getAllCompaniesEr")
    fun getCompaniesFromRemoteDataServer(
        @Header("TOKEN") token: String = "123",
        @Body requestBody: Map<String, Int>
    ): Call<CompanyDataResponse>

    companion object {
        const val BASE_URL = "http://devapp.bonusmoney.pro/"
    }
}