package com.example.studify.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.studify.R
import com.example.studify.Tool.BaseModifiers

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun productDetail(
    productName: String = "스타벅스 기프티콘",
    productImage: Int = R.drawable.statbucks1,  // drawable에 넣은 이미지
    productPrice: Int = 300,
    productDescription: String = "스타벅스에서 사용 가능한 기프티콘 입니다.",
    onBackClick: () -> Unit = {},
    onBuyClick: () -> Unit = {}, navController: NavController
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("상품 상세정보") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "뒤로가기",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = BaseModifiers.BaseTextfillModifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = productImage),
                contentDescription = productName,
                modifier = Modifier
                    .size(200.dp)
                    .padding(bottom = 16.dp),
                contentScale = ContentScale.Crop
            )


            Text(
                text = productName,
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(Modifier.height(8.dp))


            Text(
                text = "${productPrice}포인트",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Divider(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth(),
                color = MaterialTheme.colorScheme.surfaceVariant
            )


            Text(
                text = productDescription,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Spacer(Modifier.weight(1f))


            Button(
                onClick = onBuyClick,
                modifier = BaseModifiers.BaseBtnModifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("구매하기")
            }
        }
    }
}
