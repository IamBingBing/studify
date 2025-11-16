package com.example.studify.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.studify.Tool.BaseModifiers

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun productDetail(
    vm: productDetailVM = hiltViewModel(),
    navController: NavController
) {
    val productName by vm.productName
    val productImage by vm.productImageRes
    val productPrice by vm.productPrice
    val productDescription by vm.productDescription

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("상품 상세정보") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
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
                onClick = {
                    vm.requestBuy(
                        onSuccess = {
                            // TODO: 구매 성공 후 처리
                        },
                        onError = {
                            // TODO: 에러 처리
                        }
                    )
                },
                modifier = BaseModifiers.BaseBtnModifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("구매하기")
            }
        }
    }
}
