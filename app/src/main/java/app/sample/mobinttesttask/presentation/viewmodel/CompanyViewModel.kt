package app.sample.mobinttesttask.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import app.sample.mobinttesttask.data.local.model.CompanyEntity
import app.sample.mobinttesttask.data.mappers.toCompany
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class CompanyViewModel @Inject constructor(
    pager: Pager<Int, CompanyEntity>
) : ViewModel() {

    val companyPagingFlow = pager
        .flow
        .map { pagingData ->
            pagingData.map { companyEntity -> companyEntity.toCompany() }
        }
        .cachedIn(viewModelScope)
}