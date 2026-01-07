package com.liftechnology.planalimenticio.main.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.liftechnology.planalimenticio.R
import com.liftechnology.planalimenticio.main.theme.colorWhite
import com.liftechnology.planalimenticio.main.theme.onPrimaryContainerLight

@Composable
fun HeaderScreen(
    title: String,
    isSearch: Boolean = true,
    onNavigateToSearch: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(onPrimaryContainerLight),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title.uppercase(),
            fontSize = dimensionResource(id = R.dimen.size_title_card).value.sp,
            color = colorWhite,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(8.dp)
                .weight(1f)
        )

        if (isSearch)
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "Search",
                tint = colorWhite,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clickable { onNavigateToSearch() }
            )
    }
}