package app.sample.mobinttesttask.domain.model

import androidx.compose.ui.graphics.Color

data class Company(
    val id: String,
    val companyName: String,
    val logo: String?,
    val backgroundColor: Color,
    val mainColor: Color,
    val cardBackgroundColor: Color,
    val textColor: Color,
    val highlightTextColor: Color,
    val accentColor: Color,
    val mark: Int,
    val number: Int,
    val loyaltyName: String,
    val requiredSum: Int,
    val markToCash: Int,
    val cashToMark: Int
)
