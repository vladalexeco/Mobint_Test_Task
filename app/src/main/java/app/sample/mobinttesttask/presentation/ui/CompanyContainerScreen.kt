package app.sample.mobinttesttask.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import app.sample.mobinttesttask.R
import app.sample.mobinttesttask.domain.model.Company
import app.sample.mobinttesttask.presentation.theme.LightGrey
import app.sample.mobinttesttask.presentation.theme.MainBlue
import app.sample.mobinttesttask.presentation.theme.PurpleGrey80
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
                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        color = SimpleBlack
                    )
                    Text(
                        modifier = Modifier.padding(top = 12.dp),
                        text = stringResource(R.string.loading_companies),
                        style = MaterialTheme.typography.bodySmall,
                        color = SimpleBlack
                    )
                }
            } else {

                if (companies.itemCount == 0) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.no_companies),
                            style = MaterialTheme.typography.bodySmall,
                            color = SimpleBlack
                        )
                    }
                } else {
                    PullToRefreshLazyColumn(
                        items = companies,
                        content = {
                            CompanyItem(
                                company = it,
                                onEyeButtonClick = {
                                    Toast.makeText(
                                        context,
                                        "Eye Button, companyId: ${it.id}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                onTrashButtonClick = {
                                    Toast.makeText(
                                        context,
                                        "Trash Button, companyId: ${it.id}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                onDetailsButtonClick = {
                                    Toast.makeText(
                                        context,
                                        "Details Button, companyId: ${it.id}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            )
                        },
                        condition1 = companies.loadState.append is LoadState.Error,
                        condition2 = companies.loadState.append is LoadState.Loading,
                        action1 = {
                            Box(
                                modifier = Modifier
                                    .padding(12.dp)
                                    .height(28.dp)
                                    .background(
                                        color = MainBlue,
                                        shape = RoundedCornerShape(4.dp)
                                    )
                                    .clickable {
                                        companies.refresh()
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    modifier = Modifier.padding(horizontal = 6.dp),
                                    text = "Bad response. Try again.",
                                    color = LightGrey
                                )
                            }
                        },
                        action2 = {
                            Column(
                                modifier = Modifier
                                    .align(Alignment.TopCenter)
                                    .padding(top = 12.dp, bottom = 12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                CircularProgressIndicator(
                                    color = SimpleBlack
                                )
                                Text(
                                    modifier = Modifier.padding(top = 12.dp),
                                    text = stringResource(id = R.string.loading_companies),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = SimpleBlack
                                )
                            }
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
                text = stringResource(R.string.managing_maps),
                style = MaterialTheme.typography.bodyLarge,
                color = MainBlue
            )
        }
    }
}
