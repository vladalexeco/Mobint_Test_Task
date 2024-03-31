package app.sample.mobinttesttask.presentation.ui

import android.graphics.Color
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
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
import androidx.paging.compose.items
import app.sample.mobinttesttask.domain.model.Company
import app.sample.mobinttesttask.presentation.theme.LightGrey
import app.sample.mobinttesttask.presentation.theme.MainBlue
import app.sample.mobinttesttask.presentation.theme.SimpleBlack
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun CompanyScreen(
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

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Управление картами",
                style = MaterialTheme.typography.bodyLarge,
                color = MainBlue
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = LightGrey)
                .padding(horizontal = 12.dp)
        ) {

            if (companies.loadState.refresh is LoadState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
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
    }
}

@Composable
fun SimpleButton(
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier.padding(12.dp),
        onClick = { onClick.invoke() }
    ) {
        Text(text = "Server error. Retry")
    }
}