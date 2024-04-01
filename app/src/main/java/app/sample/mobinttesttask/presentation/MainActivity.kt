package app.sample.mobinttesttask.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import app.sample.mobinttesttask.presentation.theme.MobintTestTaskTheme
import app.sample.mobinttesttask.presentation.ui.CompanyContainerScreen
import app.sample.mobinttesttask.presentation.ui.CompanyScreen
import app.sample.mobinttesttask.presentation.viewmodel.CompanyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MobintTestTaskTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel = hiltViewModel<CompanyViewModel>()
                    val companies = viewModel.companyPagingFlow.collectAsLazyPagingItems()
                    CompanyContainerScreen(
                        companies = companies
                    )
                }
            }
        }
    }
}
