package app.sample.mobinttesttask.data.network

import app.sample.mobinttesttask.data.network.model.CompanyDto

data class CompanyDataResponse(
    val companies: List<CompanyDto>,
    val limit: Int,
    val offset: Int
)
