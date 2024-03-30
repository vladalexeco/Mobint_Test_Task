package app.sample.mobinttesttask.data.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("mobileapp/getAllCompaniesIdeal")
    fun getCompaniesFromRemoteDataServer(
        @Header("TOKEN") token: String = "123",
        @Body requestBody: Map<String, Any>
    ): CompanyDataResponse

    companion object {
        const val BASE_URL = "http://devapp.bonusmoney.pro/"
    }
}