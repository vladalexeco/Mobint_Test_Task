package app.sample.mobinttesttask.data.network.model

data class LoyaltyLevelDto(
    val number: Int,
    val name: String,
    val requiredSum: Int,
    val markToCash: Int,
    val cashToMark: Int
)

