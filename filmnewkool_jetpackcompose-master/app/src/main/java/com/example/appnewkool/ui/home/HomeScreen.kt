package com.example.appnewkool.ui.home

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
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
import com.example.appnewkool.ui.theme.WhiteBlue


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel? = hiltViewModel(),
    navToAddProDuctSc: () -> Unit,
    navToDetailProductSc: (Int) -> Unit
) {

    val state = viewModel?.state ?: ProductsState()
    val listProduct = state.listProduct
    val listHangXe = mutableSetOf<String?>()
    listProduct.forEach {
        if (it.hangXe != null && it.hangXe != "") listHangXe.add(it.hangXe)
    }

    if(viewModel?.toast != null){
        val context = LocalContext.current
        LaunchedEffect(viewModel.toast) {
            Toast.makeText(context, viewModel.toast, Toast.LENGTH_SHORT).show()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Column() {
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
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "",
                            tint = Color.White,
                            modifier = Modifier
                                .padding(start = 10.dp)
                                .size(30.dp)
                        )
                        SearchFeature(state.searchQuery) {
                            viewModel?.onEvent(
                                ProductsListingsEvent.OnSearchQueryChange(it)
                            )
                        }
                    }
                    Box() {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp, start = 15.dp),
                            text = "NEWKOOL FILM", color = Color.White,
                            fontSize = 40.sp,
                            fontFamily = FontFamily(
                                Font(
                                    resId = R.font.modulusmedium,
                                    weight = FontWeight.Black
                                )
                            ),
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 30.dp),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        Text(
                            text = "Film for Car",
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(6f)
                    .padding(top = 5.dp)
            ) {
                Column {

                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 7.dp)
                    ) {
                        item {
                            Chip(
                                onClick = {
                                    viewModel?.onEvent(
                                        ProductsListingsEvent.OnSearchQueryChange("")
                                    )
                                }, border = BorderStroke(
                                    ChipDefaults.OutlinedBorderSize,
                                    color = Color.Black
                                ),
                                colors = ChipDefaults.chipColors(
                                    backgroundColor = Color.Transparent,
                                    contentColor = Color.Black,
                                )
                            ) {
                                Text(text = "All")
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                        }
                        items(listHangXe.size) {


                            Chip(
                                onClick = {
                                    listHangXe.elementAt(it)
                                        ?.let {
                                            viewModel?.onEvent(
                                                ProductsListingsEvent.OnSortQueryChange(
                                                    it
                                                )
                                            )
                                        }
                                },
                                border = BorderStroke(
                                    ChipDefaults.OutlinedBorderSize,
                                    color = Color.Black
                                ),
                                colors = ChipDefaults.chipColors(
                                    backgroundColor = Color.Transparent,
                                    contentColor = Color.Black,
                                ),
                            ) {
                                listHangXe.elementAt(it)?.let { it1 -> Text(text = it1) }
                            }
                            Spacer(modifier = Modifier.width(10.dp))

                        }
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        if (viewModel?.isLoading == true) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(color = WhiteBlue)
                            }
                        } else {
                            LazyVerticalGrid(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 5.dp),
                                columns = GridCells.Fixed(2)
                            ) {
                                listProduct.size.let {
                                    items(it) {
                                        ItemInfo(listProduct.get(it)) {
                                            listProduct.get(it).id?.let { it1 ->
                                                navToDetailProductSc.invoke(
                                                    it1
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (viewModel?.token != "") {
                            ExtendedFloatingActionButton(
                                text = { Text(text = "Đồng bộ hóa") },
                                onClick = { viewModel?.onEvent(ProductsListingsEvent.Refresh) },
                                icon = {
                                    Icon(
                                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_autorenew_24),
                                        contentDescription = "",
                                    )
                                },
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(20.dp),
                                backgroundColor = BlueCus,
                                contentColor = Color.White
                            )
                            ExtendedFloatingActionButton(
                                text = { Text(text = "Thêm mới") },
                                onClick = navToAddProDuctSc,
                                icon = {
                                    Icon(
                                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_save),
                                        contentDescription = "",
                                    )
                                },
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(20.dp),
                                backgroundColor = BlueCus,
                                contentColor = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SearchFeature(search: String, seachChange: (String) -> Unit) {

    OutlinedTextField(
        value = search,
        onValueChange = seachChange,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        placeholder = {
            Text(text = "Search...")
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = Color.White
        ),
        maxLines = 1,
        singleLine = true
    )
}

@Composable
fun ItemInfo(
    product: Product,
    changeScreen: () -> Unit
) {
    Card(
        modifier = Modifier.padding(start = 5.dp, end = 5.dp, bottom = 10.dp),
        shape = RoundedCornerShape(8.dp), elevation = 1.dp, backgroundColor = BlueWhite
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .background(color = BlueWhite)
                .clickable { changeScreen.invoke() }
        ) {
            Text(
                text = product.tenXe.uppercase(),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White, shape = RoundedCornerShape(5.dp)),
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            if (product.kinhLai != null) Column() {
                Text(text = "Kính lái", color = Color.White)
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = product.kinhLai,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            if (product.suonTruoc != null)
                Column() {
                    Text(text = "Sườn trước", color = Color.White)
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = product.suonTruoc,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            if (product.suonSau != null)
                Column() {
                    Text(text = "Sườn sau", color = Color.White)
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = product.suonSau,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
        }
    }

}

@Preview(showSystemUi = true)
@Composable
fun PreHome() {
    HomeScreen(null, {}) {
    }
}

