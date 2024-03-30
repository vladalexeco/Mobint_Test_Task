package app.sample.mobinttesttask.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CompanyEntity(
    @PrimaryKey
    val id: String,
    val companyName: String,
    val logo: String,
    val backgroundColor: String,
    val mainColor: String,
    val cardBackgroundColor: String,
    val textColor: String,
    val highlightTextColor: String,
    val accentColor: String,
    val mark: Int,
    val number: Int,
    val loyaltyName: String,
    val requiredSum: Int,
    val markToCash: Int,
    val cashToMark: Int
)
