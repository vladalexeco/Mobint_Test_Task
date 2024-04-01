package app.sample.mobinttesttask.data.network

import app.sample.mobinttesttask.data.network.model.CompanyDto

interface NetworkCompanyClient
 {
     suspend fun getCompanies(offset: Int, limit: Int): List<CompanyDto>
}