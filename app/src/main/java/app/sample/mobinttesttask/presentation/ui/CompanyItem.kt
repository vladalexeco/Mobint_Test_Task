package app.sample.mobinttesttask.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.sample.mobinttesttask.R
import app.sample.mobinttesttask.domain.model.Company
import app.sample.mobinttesttask.presentation.theme.LightGrey
import app.sample.mobinttesttask.presentation.theme.MainBlue
import app.sample.mobinttesttask.presentation.theme.Pink40
import app.sample.mobinttesttask.presentation.theme.Pink80
import app.sample.mobinttesttask.presentation.theme.Purple80
import app.sample.mobinttesttask.presentation.theme.PurpleGrey80
import coil.compose.AsyncImage

@Composable
fun CompanyItem(
    company: Company,
    modifier: Modifier = Modifier,
    onEyeButtonClick: () -> Unit,
    onTrashButtonClick: () -> Unit,
    onDetailsButtonClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = company.cardBackgroundColor,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = company.companyName,
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = 6.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    color = company.highlightTextColor
                )
                AsyncImage(
                    model = company.logo,
                    contentDescription = company.companyName,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(shape = CircleShape)
                        .background(Color.LightGray)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(Color.LightGray)
            )

            Row(
                modifier = Modifier.padding(top = 12.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    modifier = Modifier.padding(end = 6.dp),
                    text = company.mark.toString(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = company.highlightTextColor
                )

                Text(
                    text = stringResource(R.string.scores),
                    style = MaterialTheme.typography.bodyMedium,
                    color = company.textColor
                )
            }

            Row(
                modifier = Modifier.padding(top = 12.dp, bottom = 6.dp)
            ) {
                Column(
                    modifier = Modifier.padding(end = 34.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 6.dp),
                        text = stringResource(R.string.cashback),
                        style = MaterialTheme.typography.bodySmall,
                        color = company.textColor
                    )

                    Row {
                        Text(
                            modifier = Modifier.padding(end = 4.dp),
                            text = company.cashToMark.toString(),
                            style = MaterialTheme.typography.bodyMedium,
                            color = company.highlightTextColor
                        )

                        Text(
                            text = "%",
                            style = MaterialTheme.typography.bodyMedium,
                            color = company.highlightTextColor
                        )
                    }
                }

                Column {
                    Text(
                        modifier = Modifier.padding(bottom = 6.dp),
                        text = stringResource(R.string.level),
                        style = MaterialTheme.typography.bodySmall,
                        color = company.textColor
                    )

                    Text(
                        text = company.loyaltyName,
                        style = MaterialTheme.typography.bodyMedium,
                        color = company.highlightTextColor
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(Color.LightGray)
            )

            Row(
                modifier = Modifier.padding(top = 6.dp, bottom = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .size(16.dp)
                        .clickable {
                            onEyeButtonClick.invoke()
                        },
                    painter = painterResource(id = R.drawable.ic_eye),
                    contentDescription = null,
                )

                Image(
                    modifier = Modifier
                        .size(16.dp)
                        .weight(1f)
                        .clickable {
                            onTrashButtonClick.invoke()
                        },
                    painter = painterResource(id = R.drawable.ic_trash),
                    contentDescription = null
                )

                Box(
                    modifier = Modifier
                        .height(28.dp)
                        .background(
                            color = company.mainColor,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .clickable {
                            onDetailsButtonClick.invoke()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 6.dp),
                        text = stringResource(R.string.detailed),
                        color = company.highlightTextColor
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CompanyItemPreview() {
    CompanyItem(
        company = Company(
            id = "1",
            companyName = "Some name",
            logo = null,
            backgroundColor = Purple80,
            mainColor = PurpleGrey80,
            cardBackgroundColor = Pink80,
            textColor = Color.Gray,
            highlightTextColor = Color.Black,
            accentColor = Pink40,
            mark = 4,
            number = 10,
            loyaltyName = "Платина",
            requiredSum = 5,
            markToCash = 19,
            cashToMark = 25,
        ),
        onEyeButtonClick = {},
        onTrashButtonClick = {},
        onDetailsButtonClick = {}
    )
}