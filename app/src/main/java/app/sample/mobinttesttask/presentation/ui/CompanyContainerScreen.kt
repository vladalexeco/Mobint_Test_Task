package app.sample.mobinttesttask.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import app.sample.mobinttesttask.domain.model.Company
import app.sample.mobinttesttask.presentation.theme.LightGrey
import app.sample.mobinttesttask.presentation.theme.MainBlue
import app.sample.mobinttesttask.presentation.theme.SimpleBlack
import app.sample.mobinttesttask.presentation.theme.SimpleWhite
import kotlinx.coroutines.launch

@Composable
fun CompanyContainerScreen(
    companies: LazyPagingItems<Company>
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = companies.loadState) {
        if (companies.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: ${(companies.loadState.refresh as LoadState.Error).error.message}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    var isRefreshing by remember {
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = LightGrey)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 64.dp, start = 12.dp, end = 12.dp)
        ) {

            if (companies.loadState.refresh is LoadState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            } else {

                if (companies.itemCount == 0) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp)
                    ) {
                        Text(
                            text = "Нет компаний",
                            style = MaterialTheme.typography.bodySmall,
                            color = SimpleBlack
                        )
                    }
                } else {
                    PullToRefreshLazyColumn(
                        items = companies,
                        content = {
                            CompanyItem(company = it)
                        },
                        condition1 = companies.loadState.append is LoadState.Error,
                        condition2 = companies.loadState.append is LoadState.Loading,
                        action1 = {
                            SimpleButton {
                                companies.refresh()
                            }
                        },
                        action2 = {
                            CircularProgressIndicator()
                        },
                        isRefreshing = isRefreshing,
                        onRefresh = {
                            scope.launch {
                                isRefreshing = true
                                companies.refresh()
                                isRefreshing = false
                            }
                        })
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .align(Alignment.TopCenter)
                .background(color = SimpleWhite),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Управление картами",
                style = MaterialTheme.typography.bodyLarge,
                color = MainBlue
            )
        }
    }
}