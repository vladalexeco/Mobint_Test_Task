package app.sample.mobinttesttask.data.mappers

import app.sample.mobinttesttask.data.local.model.CompanyEntity
import app.sample.mobinttesttask.data.network.model.CompanyDto
import app.sample.mobinttesttask.domain.model.Company

fun CompanyDto.toCompanyEntity(): CompanyEntity {
    return CompanyEntity(
        id = company.companyId,
        companyName = mobileAppDashboard.companyName,
        logo = mobileAppDashboard.logo,
        backgroundColor = mobileAppDashboard.backgroundColor,
        mainColor = mobileAppDashboard.mainColor,
        cardBackgroundColor = mobileAppDashboard.cardBackgroundColor,
        textColor = mobileAppDashboard.textColor,
        highlightTextColor = mobileAppDashboard.highlightTextColor,
        accentColor = mobileAppDashboard.accentColor,
        mark = customerMarkParameters.mark,
        number = customerMarkParameters.loyaltyLevel.number,
        loyaltyName = customerMarkParameters.loyaltyLevel.name,
        requiredSum = customerMarkParameters.loyaltyLevel.requiredSum,
        markToCash = customerMarkParameters.loyaltyLevel.markToCash,
        cashToMark = customerMarkParameters.loyaltyLevel.cashToMark
    )
}

fun CompanyEntity.toCompany(): Company {
    return Company(
        id = id,
        companyName = companyName,
        logo = logo,
        backgroundColor = backgroundColor,
        mainColor = mainColor,
        cardBackgroundColor = cardBackgroundColor,
        textColor = textColor,
        highlightTextColor = highlightTextColor,
        accentColor = accentColor,
        mark = mark,
        number = number,
        loyaltyName = loyaltyName,
        requiredSum = requiredSum,
        markToCash = markToCash,
        cashToMark = cashToMark
    )
}