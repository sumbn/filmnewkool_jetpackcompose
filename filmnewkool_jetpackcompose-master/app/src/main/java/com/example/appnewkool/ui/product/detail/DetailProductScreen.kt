package com.example.appnewkool.ui.product.detail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.appnewkool.R
import com.example.appnewkool.data.model.Product
import com.example.appnewkool.ui.theme.BlueCus
import com.example.appnewkool.ui.theme.BlueWhite


@Composable
fun DetailProductScreen(
    viewModel: DetailProductViewModel? = hiltViewModel(),
    productId: Int,
    navToUpdateProduct: ((Int) -> Unit),
    navOnBack: (() -> Unit)
) {
    BackHandler() {
        navOnBack.invoke()
    }
    viewModel?.getProduct(productId)
    val product = viewModel?.product ?: Product(
        null, "", null, null, null, null, null, null, null, null, null
    )
    Box(modifier = Modifier.background(color = Color.White)) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = BlueCus.copy(alpha = 0.2f))
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = BlueWhite,
                        shape = RoundedCornerShape(bottomEnd = 60.dp)
                    )
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 15.dp, start = 10.dp, end = 10.dp, bottom = 5.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(onClick = { navOnBack.invoke() }) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_back_ios_24),
                                contentDescription = "",
                                tint = Color.White,
                                modifier = Modifier
                                    .padding(horizontal = 10.dp)
                            )
                        }
                        if (viewModel?.token != "") {
                            IconButton(onClick = { navToUpdateProduct.invoke(productId) }) {
                                Icon(
                                    imageVector = Icons.Rounded.Edit,
                                    contentDescription = "",
                                    tint = Color.White,
                                    modifier = Modifier
                                        .padding(horizontal = 10.dp)
                                )
                            }
                        }

                    }

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 15.dp),
                        text = "NEWKOOL FILM", color = Color.White,
                        fontSize = 40.sp,
                        fontFamily = FontFamily(
                            Font(
                                resId = R.font.modulusmedium,
                                weight = FontWeight.Black
                            )
                        ),
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 30.dp),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        Text(
                            text = "Detail",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontFamily = FontFamily(
                                Font(
                                    resId = R.font.modulusbold
                                )
                            ),
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
            Box(modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())) {
                Card(
                    modifier = Modifier
                        .padding(30.dp),
                    shape = RoundedCornerShape(30.dp),
                    elevation = 20.dp,
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (product != null) {

                            Text(
                                text = product.tenXe,
                                color = Color.Black,
                                fontSize = 40.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                            Spacer(modifier = Modifier.height(3.dp))
                            product.hangXe?.let { Text(text = it, fontSize = 20.sp) }
                            Spacer(modifier = Modifier.height(10.dp))
                            product.kinhLai?.let {
                                ItemDetail(name = "Kính lái", value = it)
                            }
                            product.suonTruoc?.let {
                                ItemDetail(name = "Sườn trước", value = it)
                            }

                            product.suonSau?.let {
                                ItemDetail(name = "Sườn sau", value = it)
                            }

                            product.khoangSau?.let {
                                ItemDetail(name = "Khoang sau", value = it)
                            }
                            product.kinhHau?.let {
                                ItemDetail(name = "Kính hậu", value = it)
                            }

                            product.tamGiac?.let {
                                ItemDetail(name = "Tam giác", value = it)
                            }
                            product.noc?.let {
                                ItemDetail(name = "Nóc", value = it)
                            }

                        }
                    }
                }

            }

        }
    }
}


@Composable
fun ItemDetail(name: String, value: String) {
    Column() {
        Spacer(modifier = Modifier.height(10.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = BlueWhite, shape = RoundedCornerShape(30.dp))
                .padding(bottom = 6.dp)
        ) {
            Text(
                text = name, modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 3.dp, start = 30.dp),
                color = Color.White,
                fontSize = 13.sp
            )
            Text(
                value,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 19.sp,
                color = Color.White
            )

        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun PreDetailScreen() {
    DetailProductScreen(productId = 1, viewModel = null, navToUpdateProduct = {}) {}
}