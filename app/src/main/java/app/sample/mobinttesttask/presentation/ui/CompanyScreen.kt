package app.sample.mobinttesttask.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import app.sample.mobinttesttask.domain.model.Company

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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp)
    ) {

        if (companies.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(companies) { company ->
                    if (company != null) {
                        CompanyItem(
                            company = company,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
                if (companies.loadState.append is LoadState.Error) {
                    item {
                        Button(
                            modifier = Modifier.padding(12.dp),
                            onClick = { companies.refresh() }
                        ) {
                            Text(text = "Server error. Retry")
                        }
                    }
                } else {
                    item {
                        if (companies.loadState.append is LoadState.Loading) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}